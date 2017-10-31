/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDeCondicional;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Condicional;
import br.com.onesystem.domain.ConfiguracaoVenda;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.LayoutDeImpressao;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.GeradorDeEstoque;
import br.com.onesystem.util.ImpressoraDeLayoutGrafico;
import br.com.onesystem.util.ImpressoraDeLayoutTexto;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.valueobjects.TipoImpressao;
import br.com.onesystem.valueobjects.TipoLayout;
import br.com.onesystem.war.builder.ItemDeCondicionalBV;
import br.com.onesystem.war.builder.CondicionalBV;
import br.com.onesystem.war.service.CotacaoService;
import br.com.onesystem.war.service.LayoutDeImpressaoService;
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
public class CondicionalView extends BasicMBImpl<Condicional, CondicionalBV> implements Serializable {

    private ItemDeCondicionalBV itemDeCondicional;
    private ItemDeCondicional itemDeCondicionalSelecionado;
    private List<ItemDeCondicional> itensDeCondicionais;
    private Cotacao cotacao;
    private LayoutDeImpressao layout;

    @Inject
    private Configuracao configuracao;

    @Inject
    private ConfiguracaoVenda configuracaoVenda;

    @Inject
    private CotacaoService cotacaoService;

    @Inject
    private LayoutDeImpressaoService layoutService;

    @Inject
    private GeradorDeEstoque geradorDeEstoque;

    // ---------------------- Inicializa Janela -------------------------------
    @PostConstruct
    public void init() {
        iniciarConfiguracoes();
        limparJanela();
    }

    private void iniciarConfiguracoes() {
        try {
            cotacao = cotacaoService.getCotacaoPadrao(new Date());
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void limparJanela() {
        try {
            e = new CondicionalBV();
            e.setOperacao(configuracaoVenda.getOperacaoDeCondicional());
            e.setCotacao(cotacao);
            itemDeCondicional = new ItemDeCondicionalBV();
            itensDeCondicionais = new ArrayList<>();
            e.setFilial((Filial) SessionUtil.getObject("filial", FacesContext.getCurrentInstance()));
            limpaSessao();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
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
     * Gera a entidade com os dados de recebimento, parcelas,
     * itensDeCondicionais e cotações.
     */
    public void finalizar() {
        try {
            preparaInclusaoDeItemDeCondicional();

            //Constroi a condicional
            Condicional condicional = e.construirComID();
            geradorDeEstoque.geraEstoque(condicional);

            addNoBanco(condicional);
            t = condicional;
            layout = layoutService.getLayoutPorTipoDeLayout(TipoLayout.CONDICIONAL);
            if (!layout.getTipoImpressao().equals(TipoImpressao.NADA_A_FAZER)) {
                if (layout.isLayoutGraficoEhPadrao()) {
                    RequestContext.getCurrentInstance().execute("document.getElementById('conteudo:ne:imprimir').click()"); // chama a impressao
                } else {
                    RequestContext.getCurrentInstance().execute("document.getElementById('conteudo:ne:imprimirTexto').click()"); // chama a impressao
                }
            }
        } catch (DadoInvalidoException die) {
            die.printConsole();
            die.print();
        } catch (ConstraintViolationException cpe) {
            ErrorMessage.printConsole(cpe.toString());
            ErrorMessage.print(cpe.toString());
        }
    }

    /**
     * Imprime o layout da condicional.
     *
     */
    public void imprimir() {
        try {
            new ImpressoraDeLayoutGrafico(t.getItensDeCondicional(), layout).addParametro("condicional", t).visualizarPDF();
            t = null; // libera memoria do objeto impresso.
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void imprimirTexto() {
        try {
            new ImpressoraDeLayoutTexto(layout.getLayoutTexto(), Condicional.class, t).imprimir(configuracao.getCaminhoImpressoraTexto());
            t = null;
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void preparaInclusaoDeItemDeCondicional() throws DadoInvalidoException {
        for (ItemDeCondicional i : itensDeCondicionais) {
            e.getItensDeCondicional().add(new ItemDeCondicionalBV(i).construir());
        }
    }

    // -------------- Fim Operações para criação da entidade ------------------
    // ----------------------------- Itens ------------------------------------
    public void addItemNaLista() {
        try {
            itemDeCondicional.setId(getCodigoItem());
            ItemDeCondicional ie = itemDeCondicional.construirComId();
            itensDeCondicionais.add(ie);
            limparItemDeCondicional();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void updateItemNaLista() {
        try {
            if (itemDeCondicionalSelecionado != null) {
                itensDeCondicionais.set(itensDeCondicionais.indexOf(itemDeCondicionalSelecionado),
                        itemDeCondicional.construirComId());
                limparItemDeCondicional();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void deleteItemNaLista() {
        if (itemDeCondicionalSelecionado != null) {
            itensDeCondicionais.remove(itemDeCondicionalSelecionado);
            limparItemDeCondicional();
        }
    }

    public void limparItemDeCondicional() {
        itemDeCondicional = new ItemDeCondicionalBV();
        itemDeCondicionalSelecionado = null;
    }

    public BigDecimal getTotalItens() {
        return itensDeCondicionais.stream().map(ItemDeCondicional::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
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
        } else if (obj instanceof Pessoa) {
            e.setPessoa((Pessoa) obj);
        } else if (obj instanceof Item) {
            itemDeCondicional.setItem((Item) obj);
        }
    }

    public void selecionaItemDeCondicional(SelectEvent event) {
        this.itemDeCondicionalSelecionado = (ItemDeCondicional) event.getObject();
        this.itemDeCondicional = new ItemDeCondicionalBV(itemDeCondicionalSelecionado);
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
    public BigDecimal getTotalCondicional() {
        BigDecimal acrescimo = e.getAcrescimo() == null ? BigDecimal.ZERO : e.getAcrescimo();
        BigDecimal frete = e.getFrete() == null ? BigDecimal.ZERO : e.getFrete();
        BigDecimal despesaCobranca = e.getDespesaCobranca() == null ? BigDecimal.ZERO : e.getDespesaCobranca();
        BigDecimal desconto = e.getDesconto() == null ? BigDecimal.ZERO : e.getDesconto();

        return getTotalItens().add(acrescimo.add(frete.add(despesaCobranca)).subtract(desconto));
    }

    private Long getCodigoItem() {
        Long id = (long) 1;
        if (!itensDeCondicionais.isEmpty()) {
            for (ItemDeCondicional dp : itensDeCondicionais) {
                if (dp.getId() >= id) {
                    id = dp.getId() + 1;
                }
            }
        }
        return id;
    }

    //------------------- Fim Getter Personalizados ---------------------------
    //----------------------- Getters and Setters -----------------------------
    public ItemDeCondicionalBV getItemDeCondicional() {
        return itemDeCondicional;
    }

    public void setItemDeCondicional(ItemDeCondicionalBV itemDeCondicional) {
        this.itemDeCondicional = itemDeCondicional;
    }

    public ItemDeCondicional getItemDeCondicionalSelecionado() {
        return itemDeCondicionalSelecionado;
    }

    public void setItemDeCondicionalSelecionado(ItemDeCondicional itemDeCondicionalSelecionado) {
        this.itemDeCondicionalSelecionado = itemDeCondicionalSelecionado;
    }

    public List<ItemDeCondicional> getItensDeCondicionais() {
        return itensDeCondicionais;
    }

    public void setItensDeCondicionais(List<ItemDeCondicional> itensDeCondicionais) {
        this.itensDeCondicionais = itensDeCondicionais;
    }

    public Configuracao getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }

    public ConfiguracaoVenda getConfiguracaoVenda() {
        return configuracaoVenda;
    }

    public void setConfiguracaoVenda(ConfiguracaoVenda configuracaoVenda) {
        this.configuracaoVenda = configuracaoVenda;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public void setCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
    }

    public CotacaoService getCotacaoService() {
        return cotacaoService;
    }

    public void setCotacaoService(CotacaoService cotacaoService) {
        this.cotacaoService = cotacaoService;
    }
//------------------- Fim Getters and Setters -----------------------------

}
