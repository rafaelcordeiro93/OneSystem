/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDeComanda;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Comanda;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.valueobjects.EstadoDeComanda;
import br.com.onesystem.war.builder.ItemDeComandaBV;
import br.com.onesystem.war.builder.ComandaBV;
import br.com.onesystem.war.service.ComandaService;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.CotacaoService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ComandaView extends BasicMBImpl<Comanda, ComandaBV> implements Serializable {

    private List<Comanda> comandasAbertas;
    private ItemDeComandaBV itemDeComanda;
    private ItemDeComanda itemDeComandaSelecionado;
    private List<ItemDeComanda> itensDeComandas;
    private Configuracao configuracao;
    private Cotacao cotacao;

    @Inject
    private ConfiguracaoService configuracaoService;

    @Inject
    private CotacaoService cotacaoService;

    @Inject
    private ComandaService service;

    // ---------------------- Inicializa Janela -------------------------------
    @PostConstruct
    public void init() {
        iniciarConfiguracoes();
        limparJanela();
    }

    private void iniciarConfiguracoes() {
        try {
            comandasAbertas = service.buscarComandasNo(EstadoDeComanda.EM_DEFINICAO);
            configuracao = configuracaoService.buscar();
            cotacao = new CotacaoDAO().porMoeda(configuracao.getMoedaPadrao()).naMaiorEmissao(new Date()).resultado();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void limparJanela() {
        e = new ComandaBV();
        e.setCotacao(cotacao);
        itemDeComanda = new ItemDeComandaBV();
        itensDeComandas = new ArrayList<>();
        limpaSessao();
    }

    private void limpaSessao() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        if (session.getAttribute("onesystem.item.token") != null) {
            session.removeAttribute("onesystem.item.token");
        }
    }

    // --------------------- Fim Inicializa Janela ----------------------------
    // -------------- Operações para criação da entidade ----------------------   
    /**
     * Gera a entidade com os dados de recebimento, parcelas, itens e cotações.
     */
    public void informaNumeroComanda() {
        e.setNumeroComanda(comandasAbertas.stream().mapToInt(Comanda::getNumeroComanda).max().orElse(0) + 1);
        RequestContext.getCurrentInstance().execute("PF('numeroComandaDialog').show()");
    }

    public void finalizar() {
        try {
            preparaInclusaoDeItemDeComanda();

            //Constroi o orçamento
            Comanda comanda = e.construirComID();

            if (e.getNumeroComanda() == null) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Numero_Comanda_Not_Null"));
            } else if (comandasAbertas.stream().anyMatch(c -> c.getNumeroComanda().equals(e.getNumeroComanda()))) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Existe_Comanda_Em_Aberto_Com_Esse_Numero_Informe_Outro"));
            }

            addNoBanco(comanda);

        } catch (DadoInvalidoException die) {
            die.printConsole();
            die.print();
        } catch (ConstraintViolationException cpe) {
            ErrorMessage.printConsole(cpe.toString());
            ErrorMessage.print(cpe.toString());
        }
    }

    private void preparaInclusaoDeItemDeComanda() throws DadoInvalidoException {
        for (ItemDeComanda i : itensDeComandas) {
            e.getItensDeComanda().add(new ItemDeComandaBV(i).construir());
        }
    }

    // -------------- Fim Operações para criação da entidade ------------------
    // ----------------------------- Itens ------------------------------------
    public void addItemNaLista() {
        try {
            itemDeComanda.setId(getCodigoItem());
            ItemDeComanda ie = itemDeComanda.construirComId();
            itensDeComandas.add(ie);
            limparItemDeComanda();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void updateItemNaLista() {
        try {
            if (itemDeComandaSelecionado != null) {
                itensDeComandas.set(itensDeComandas.indexOf(itemDeComandaSelecionado),
                        itemDeComanda.construirComId());
                limparItemDeComanda();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void deleteItemNaLista() {
        if (itemDeComandaSelecionado != null) {
            itensDeComandas.remove(itemDeComandaSelecionado);
            limparItemDeComanda();
        }
    }

    public void limparItemDeComanda() {
        itemDeComanda = new ItemDeComandaBV();
        itemDeComandaSelecionado = null;
    }

    public BigDecimal getTotalItens() {
        return itensDeComandas.stream().map(ItemDeComanda::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getTotalItensFormatado() {
        return MoedaFormatter.format(cotacao.getConta().getMoeda(), getTotalItens());
    }

    // -------------------------- Fim Itens -----------------------------------
    // ----------------------------- Selecao ----------------------------------
    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        String idComponent = event.getComponent().getId();
        if (obj instanceof ListaDePreco) {
            e.setListaDePreco((ListaDePreco) obj);
        } else if (obj instanceof Item) {
            itemDeComanda.setItem((Item) obj);
        }
    }

    public void selecionaItemDeComanda(SelectEvent event) {
        this.itemDeComandaSelecionado = (ItemDeComanda) event.getObject();
        this.itemDeComanda = new ItemDeComandaBV(itemDeComandaSelecionado);
    }

    // ----------------------------- Fim Selecao ------------------------------
    // ------------------ Outras Operações da Janela --------------------------
    /**
     * Calcula o valor de acréscimo e desconto após informar um dos campos de
     * porcentagem de acréscimo e desconto.
     */
    public void calculaValorAcrescimoEDesconto() {
        BigDecimal total = getTotalItens();
        if (total.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal pAcrescimo = e.getPorcentagemAcrescimo() == null ? BigDecimal.ZERO : e.getPorcentagemAcrescimo();
            BigDecimal pDesconto = e.getPorcentagemDesconto() == null ? BigDecimal.ZERO : e.getPorcentagemDesconto();
            BigDecimal acrescimo;
            BigDecimal desconto;
            BigDecimal cem = new BigDecimal(100);

            if (pAcrescimo.compareTo(BigDecimal.ZERO) > 0) {
                acrescimo = (pAcrescimo.multiply(total)).divide(cem, 2, BigDecimal.ROUND_UP);
                e.setAcrescimo(acrescimo);
            } else {
                e.setAcrescimo(BigDecimal.ZERO);
            }
            if (pDesconto.compareTo(BigDecimal.ZERO) > 0) {
                desconto = (pDesconto.multiply((total))).divide(cem, 2, BigDecimal.ROUND_UP);
                e.setDesconto(desconto);
            } else {
                e.setDesconto(BigDecimal.ZERO);
            }
        }
    }

    /**
     * Calcula a porcentagem de acréscimo e desconto após informar um dos campos
     * de valor de acréscimo e desconto.
     */
    public void calculaPorcentagemAcrescimoEDesconto() {
        BigDecimal total = getTotalItens();
        if (total.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal acrescimo = e.getAcrescimo() == null ? BigDecimal.ZERO : e.getAcrescimo();
            BigDecimal desconto = e.getDesconto() == null ? BigDecimal.ZERO : e.getDesconto();
            BigDecimal pAcrescimo;
            BigDecimal pDesconto;
            BigDecimal cem = new BigDecimal(100);

            if (acrescimo.compareTo(BigDecimal.ZERO) > 0) {
                pAcrescimo = (acrescimo.multiply(cem)).divide(total, 2, BigDecimal.ROUND_UP);
                e.setPorcentagemAcrescimo(pAcrescimo);
            } else {
                e.setPorcentagemAcrescimo(BigDecimal.ZERO);
            }
            if (desconto.compareTo(BigDecimal.ZERO) > 0) {
                pDesconto = (desconto.multiply(cem)).divide(((total)), 2, BigDecimal.ROUND_UP);
                e.setPorcentagemDesconto(pDesconto);
            } else {
                e.setPorcentagemDesconto(BigDecimal.ZERO);
            }
        }
    }

    // ---------------- Fim Outras Operações da Janela ------------------------
    //----------------------- Getter Personalizados ---------------------------
    public BigDecimal getTotalComanda() {
        BigDecimal acrescimo = e.getAcrescimo() == null ? BigDecimal.ZERO : e.getAcrescimo();
        BigDecimal frete = e.getFrete() == null ? BigDecimal.ZERO : e.getFrete();
        BigDecimal despesaCobranca = e.getDespesaCobranca() == null ? BigDecimal.ZERO : e.getDespesaCobranca();
        BigDecimal desconto = e.getDesconto() == null ? BigDecimal.ZERO : e.getDesconto();

        return getTotalItens().add(acrescimo.add(frete.add(despesaCobranca)).subtract(desconto));
    }

    private Long getCodigoItem() {
        Long id = (long) 1;
        if (!itensDeComandas.isEmpty()) {
            for (ItemDeComanda dp : itensDeComandas) {
                if (dp.getId() >= id) {
                    id = dp.getId() + 1;
                }
            }
        }
        return id;
    }

    //------------------- Fim Getter Personalizados ---------------------------
    //----------------------- Getters and Setters -----------------------------
    public ItemDeComandaBV getItemDeComanda() {
        return itemDeComanda;
    }

    public void setItemDeComanda(ItemDeComandaBV itemDeComanda) {
        this.itemDeComanda = itemDeComanda;
    }

    public ItemDeComanda getItemDeComandaSelecionado() {
        return itemDeComandaSelecionado;
    }

    public void setItemDeComandaSelecionado(ItemDeComanda itemDeComandaSelecionado) {
        this.itemDeComandaSelecionado = itemDeComandaSelecionado;
    }

    public List<ItemDeComanda> getItensDeComandas() {
        return itensDeComandas;
    }

    public void setItensDeComandas(List<ItemDeComanda> itensDeComandas) {
        this.itensDeComandas = itensDeComandas;
    }

    public Configuracao getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public void setCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
    }

    public ConfiguracaoService getConfiguracaoService() {
        return configuracaoService;
    }

    public void setConfiguracaoService(ConfiguracaoService configuracaoService) {
        this.configuracaoService = configuracaoService;
    }

    public CotacaoService getCotacaoService() {
        return cotacaoService;
    }

    public void setCotacaoService(CotacaoService cotacaoService) {
        this.cotacaoService = cotacaoService;
    }

    public ComandaService getService() {
        return service;
    }

    public void setService(ComandaService service) {
        this.service = service;
    }

//------------------- Fim Getters and Setters -----------------------------
}
