/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemOrcado;
import br.com.onesystem.domain.LayoutDeImpressao;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.ImpressoraDeLayout;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.valueobjects.ModalidadeDeCobranca;
import br.com.onesystem.valueobjects.TipoImpressao;
import br.com.onesystem.valueobjects.TipoLayout;
import br.com.onesystem.war.builder.ItemOrcadoBV;
import br.com.onesystem.war.builder.OrcamentoBV;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.CotacaoService;
import br.com.onesystem.war.service.LayoutDeImpressaoService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.http.protocol.RequestContent;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.context.RequestContext;
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
    private Configuracao configuracao;
    private Cotacao cotacao;
    private LayoutDeImpressao layout;

    @Inject
    private ConfiguracaoService configuracaoService;

    @Inject
    private CotacaoService service;

    @Inject
    private LayoutDeImpressaoService layoutService;

    // ---------------------- Inicializa Janela -------------------------------
    @PostConstruct
    public void init() {
        iniciarConfiguracoes();
        limparJanela();
    }

    private void iniciarConfiguracoes() {
        try {
            layout = layoutService.getLayoutPorTipoDeLayout(TipoLayout.ORCAMENTO);
            configuracao = configuracaoService.buscar();
            cotacao = service.getCotacaoPadrao(new Date());
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void limparJanela() {
        try {
            e = new OrcamentoBV();
            e.setCotacao(cotacao);
            e.setFilial((Filial) SessionUtil.getObject("filial", FacesContext.getCurrentInstance()));
            itemOrcado = new ItemOrcadoBV();
            itemOrcadoSelecionado = new ItemOrcado();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    // --------------------- Fim Inicializa Janela ----------------------------
    // -------------- Operações para criação da entidade ----------------------   
    /**
     * Gera a entidade com os dados de recebimento, parcelas, itens e cotações.
     */
    public void finalizar() {
        try {
            //Constroi o orçamento
            Orcamento orcamento = e.construirComID();

            addNoBanco(orcamento);
            t = orcamento; // adiciona o orcamento ao objeto para impressao.
            if (!layout.getTipoImpressao().equals(TipoImpressao.NADA_A_FAZER)) {
                RequestContext.getCurrentInstance().execute("document.getElementById('conteudo:ne:imprimir').click()"); // chama a impressao
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
     * Imprime o layout do orçamento.
     *
     */
    public void imprimir() {
        try {
            new ImpressoraDeLayout(t.getItensOrcados(), layout).addParametro("orcamento", t).visualizarPDF();
            t = null; // libera memoria do objeto impresso.
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    // -------------- Fim Operações para criação da entidade ------------------
    // ----------------------------- Itens ------------------------------------
    public void addItemNaLista() {
        try {
            e.adiciona(itemOrcado);
            limparItemOrcado();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void updateItemNaLista() {
        try {
            if (itemOrcadoSelecionado != null) {
                e.atualiza(itemOrcadoSelecionado, itemOrcado.construirComId());
                limparItemOrcado();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void deleteItemNaLista() {
        if (itemOrcadoSelecionado != null) {
            e.remove(itemOrcadoSelecionado);
            limparItemOrcado();
        }
    }

    public void limparItemOrcado() {
        itemOrcado = new ItemOrcadoBV();
        itemOrcadoSelecionado = null;
    }

    // -------------------------- Fim Itens -----------------------------------
    // ----------------------------- Selecao ----------------------------------
    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        String idComponent = event.getComponent().getId();
        if (obj instanceof Orcamento) {
            e = new OrcamentoBV((Orcamento) obj);
        } else if (obj instanceof Pessoa) {
            e.setPessoa((Pessoa) obj);
        } else if (obj instanceof ListaDePreco) {
            e.setListaDePreco((ListaDePreco) obj);
        } else if (obj instanceof Item) {
            itemOrcado.setItem((Item) obj);
        } else if (obj instanceof FormaDeRecebimento) {
            FormaDeRecebimento formaDeRecebimento = (FormaDeRecebimento) obj;
            e.setFormaDeRecebimento(formaDeRecebimento);
        }
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
        BigDecimal total = e.getTotalItens();
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
        BigDecimal total = e.getTotalItens();
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

        return e.getTotalItens().add(acrescimo.add(frete.add(despesaCobranca)).subtract(desconto));
    }

    //------------------- Fim Getter Personalizados ---------------------------
    //----------------------- Getters and Setters -----------------------------
    public List<ModalidadeDeCobranca> getTiposDeFormaDeRecebimentoParcela() {
        List<ModalidadeDeCobranca> forma = new ArrayList<>();
        if (e.getFormaDeRecebimento().isParcelaEmCartao()) {
            forma.add(ModalidadeDeCobranca.CARTAO);
        }
        if (e.getFormaDeRecebimento().isParcelaEmCheque()) {
            forma.add(ModalidadeDeCobranca.CHEQUE);
        }
        if (e.getFormaDeRecebimento().isParcelaEmConta()) {
            forma.add(ModalidadeDeCobranca.TITULO);
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

    public LayoutDeImpressaoService getLayoutService() {
        return layoutService;
    }

    public void setLayoutService(LayoutDeImpressaoService layoutService) {
        this.layoutService = layoutService;
    }

}

//------------------- Fim Getters and Setters -----------------------------

