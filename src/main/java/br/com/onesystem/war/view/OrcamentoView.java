/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemOrcado;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.MoedaFomatter;
import br.com.onesystem.valueobjects.TipoFormaDeRecebimentoParcela;
import br.com.onesystem.war.builder.ItemOrcadoBV;
import br.com.onesystem.war.builder.OrcamentoBV;
import br.com.onesystem.war.builder.QuantidadeDeItemBV;
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
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class OrcamentoView extends BasicMBImpl<Orcamento, OrcamentoBV> implements Serializable {

    private ItemOrcadoBV itemOrcado;
    private ItemOrcado itemOrcadoSelecionado;
    private List<ItemOrcado> itensOrcados;
    private Configuracao configuracao;
    private Cotacao cotacao;

    @Inject
    private ConfiguracaoService configuracaoService;

    @Inject
    private CotacaoService service;

    // ---------------------- Inicializa Janela -------------------------------
    @PostConstruct
    public void init() {
        iniciarConfiguracoes();
        limparJanela();
    }

    private void iniciarConfiguracoes() {
        try {
            configuracao = configuracaoService.buscar();
            cotacao = new CotacaoDAO().buscarCotacoes().porMoeda(configuracao.getMoedaPadrao()).naMaiorEmissao(new Date()).resultado();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void limparJanela() {
        e = new OrcamentoBV();
        e.setCotacao(cotacao);
        itemOrcado = new ItemOrcadoBV();
        itensOrcados = new ArrayList<>();
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
    public void finalizar() {
        try {

            preparaInclusaoDeItemOrcado();

            //Constroi o orçamento
            Orcamento ne = e.construirComID();

            addNoBanco(ne);

        } catch (DadoInvalidoException die) {
            die.printConsole();
            die.print();
        } catch (ConstraintViolationException cpe) {
            ErrorMessage.printConsole(cpe.toString());
            ErrorMessage.print(cpe.toString());
        }
    }

    private void preparaInclusaoDeItemOrcado() throws DadoInvalidoException {
        for (ItemOrcado i : itensOrcados) {
            e.getItensOrcados().add(new ItemOrcadoBV(i).setId(null).construir());
        }
    }

    // -------------- Fim Operações para criação da entidade ------------------
    // ----------------------------- Itens ------------------------------------
    public void addItemNaLista() {
        try {
            itemOrcado.setId(getCodigoItem());
            ItemOrcado ie = itemOrcado.construirComId();
            itensOrcados.add(ie);
            limparItemOrcado();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void updateItemNaLista() {
        try {
            if (itemOrcadoSelecionado != null) {
                itensOrcados.set(itensOrcados.indexOf(itemOrcadoSelecionado),
                        itemOrcado.construirComId());
                limparItemOrcado();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void deleteItemNaLista() {
        if (itemOrcadoSelecionado != null) {
            itensOrcados.remove(itemOrcadoSelecionado);
            limparItemOrcado();
        }
    }

    public void limparItemOrcado() {
        itemOrcado = new ItemOrcadoBV();
        itemOrcadoSelecionado = null;
    }

    public BigDecimal getTotalItens() {
        return itensOrcados.stream().map(ItemOrcado::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getTotalItensFormatado() {
        return MoedaFomatter.format(cotacao.getConta().getMoeda(), getTotalItens());
    }

    // -------------------------- Fim Itens -----------------------------------
    // ----------------------------- Selecao ----------------------------------
    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        String idComponent = event.getComponent().getId();
        if (obj instanceof Pessoa) {
            e.setPessoa((Pessoa) obj);
        } else if (obj instanceof ListaDePreco) {
            e.setListaDePreco((ListaDePreco) obj);
        } else if (obj instanceof Item) {
            itemOrcado.setItem((Item) obj);
            atribuiItemASessao();
        } else if (obj instanceof FormaDeRecebimento) {
            FormaDeRecebimento formaDeRecebimento = (FormaDeRecebimento) obj;
            e.setFormaDeRecebimento(formaDeRecebimento);
        } else if (obj instanceof List) {
            List<QuantidadeDeItemBV> lista = (List<QuantidadeDeItemBV>) event.getObject();
            itemOrcado.setQuantidade(lista.stream().map(QuantidadeDeItemBV::getQuantidade).reduce(BigDecimal.ZERO, BigDecimal::add));
        }
    }

    public void atribuiItemASessao() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.setAttribute("onesystem.item.token", itemOrcado.getItem());
    }

    public void selecionaItemOrcado(SelectEvent event) {
        this.itemOrcadoSelecionado = (ItemOrcado) event.getObject();
        this.itemOrcado = new ItemOrcadoBV(itemOrcadoSelecionado);
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
    public BigDecimal getTotalOrcamento() {
        BigDecimal acrescimo = e.getAcrescimo() == null ? BigDecimal.ZERO : e.getAcrescimo();
        BigDecimal frete = e.getFrete() == null ? BigDecimal.ZERO : e.getFrete();
        BigDecimal despesaCobranca = e.getDespesaCobranca() == null ? BigDecimal.ZERO : e.getDespesaCobranca();
        BigDecimal desconto = e.getDesconto() == null ? BigDecimal.ZERO : e.getDesconto();

        return getTotalItens().add(acrescimo.add(frete.add(despesaCobranca)).subtract(desconto));
    }

    private Long getCodigoItem() {
        Long id = (long) 1;
        if (!itensOrcados.isEmpty()) {
            for (ItemOrcado dp : itensOrcados) {
                if (dp.getId() >= id) {
                    id = dp.getId() + 1;
                }
            }
        }
        return id;
    }

    //------------------- Fim Getter Personalizados ---------------------------
    //----------------------- Getters and Setters -----------------------------
    public List<TipoFormaDeRecebimentoParcela> getTiposDeFormaDeRecebimentoParcela() {
        List<TipoFormaDeRecebimentoParcela> forma = new ArrayList<>();
        if (e.getFormaDeRecebimento().isParcelaEmCartao()) {
            forma.add(TipoFormaDeRecebimentoParcela.CARTAO);
        }
        if (e.getFormaDeRecebimento().isParcelaEmCheque()) {
            forma.add(TipoFormaDeRecebimentoParcela.CHEQUE);
        }
        if (e.getFormaDeRecebimento().isParcelaEmConta()) {
            forma.add(TipoFormaDeRecebimentoParcela.TITULO);
        }
        return forma;
    }

    public ItemOrcadoBV getItemOrcado() {
        return itemOrcado;
    }

    public void setItemOrcado(ItemOrcadoBV itemOrcado) {
        this.itemOrcado = itemOrcado;
    }

    public ItemOrcado getItemOrcadoSelecionado() {
        return itemOrcadoSelecionado;
    }

    public void setItemOrcadoSelecionado(ItemOrcado itemOrcadoSelecionado) {
        this.itemOrcadoSelecionado = itemOrcadoSelecionado;
    }

    public Configuracao getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }

    public ConfiguracaoService getConfiguracaoService() {
        return configuracaoService;
    }

    public void setConfiguracaoService(ConfiguracaoService configuracaoService) {
        this.configuracaoService = configuracaoService;
    }

    public CotacaoService getService() {
        return service;
    }

    public void setService(CotacaoService service) {
        this.service = service;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public void setCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
    }

    public List<ItemOrcado> getItensOrcados() {
        return itensOrcados;
    }

    public void setItensOrcados(List<ItemOrcado> itensOrcados) {
        this.itensOrcados = itensOrcados;
    }

}

//------------------- Fim Getters and Setters -----------------------------

