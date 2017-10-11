package br.com.onesystem.war.view.dialogo;

import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.ConhecimentoDeFrete;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Fatura;
import br.com.onesystem.domain.FaturaEmitida;
import br.com.onesystem.domain.FaturaRecebida;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Movimento;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.ValorPorCotacao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.war.builder.ValorPorCotacaoBV;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.valueobjects.ModalidadeDeCobranca;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class DialogoCotacaoView extends BasicMBImpl<ValorPorCotacao, ValorPorCotacaoBV> implements Serializable {

    private List<ValorPorCotacaoBV> cotacoes;
    private Nota nota;
    private Fatura fatura;
    private ConhecimentoDeFrete conhecimentoDeFrete;
    private BigDecimal dinheiro;
    private Movimento movimento;

    @Inject
    private CotacaoDAO cotacaoDAO;
    
    @PostConstruct
    public void init() {
        try {
            limparJanela();
            buscaDaSessao();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    /**
     * @author Rafael Fernando Rauber
     *
     * Método utilizado para buscar os objetos da sessão
     */
    private void buscaDaSessao() throws DadoInvalidoException {
        FacesContext context = FacesContext.getCurrentInstance();
        nota = (Nota) SessionUtil.getObject("nota", context);
        if (nota != null) {
            inicializaObjetoDaSessao(nota.getEmissao(), nota.getCotacao().getConta().getMoeda(), nota.getTotalEmDinheiro());
            return;
        }
        // =====================================================================
        // Rafael Fernando Rauber - 01/09/2017 - Readequação de Fatura Emitida/Recebida para Fatura 
        fatura = (Fatura) SessionUtil.getObject("fatura", context);
        if (fatura != null) {
            inicializaObjetoDaSessao(fatura.getEmissao(), fatura.getMoedaPadrao(), fatura.getDinheiro());
            return;
        }
        // =====================================================================
        conhecimentoDeFrete = (ConhecimentoDeFrete) SessionUtil.getObject("conhecimentoDeFrete", context);
        if (conhecimentoDeFrete != null) {
            inicializaObjetoDaSessao(conhecimentoDeFrete.getEmissao(), conhecimentoDeFrete.getMoedaPadrao(), conhecimentoDeFrete.getDinheiro());
            return;
        }
        // =====================================================================
        movimento = (Movimento) SessionUtil.getObject("movimento", context);
        if (movimento != null) {
            inicializaObjetoDaSessao(movimento.getEmissao(), movimento.getCotacaoPadrao().getConta().getMoeda(), movimento.getTotalEmDinheiro());
            return;
        }
    }

    private void inicializaObjetoDaSessao(Date emissao, Moeda moeda, BigDecimal dinheiro) {
        inicializaCotacoes(emissao, moeda);
        this.dinheiro = dinheiro;
        calculaCotacoes();
    }

    private void inicializaCotacoes(Date emissao, Moeda moedaPadrao) {
        List<Cotacao> cotacaoLista = cotacaoDAO.naMaiorEmissao(emissao).listaDeResultados();
        for (Cotacao c : cotacaoLista) {
            cotacoes.add(new ValorPorCotacaoBV(c, null, null, null, moedaPadrao));
        }
    }

    /**
     * Busca a moeda padrão e abre a janela de cotações com o valor restante na
     * cotação de cada moeda.
     */
    public void calculaCotacoes() {
        for (ValorPorCotacaoBV c : cotacoes) {
            c.setTotal(dinheiro);
            c.setTotalConvertidoRecebido(getTotalConvertidoRecebido());
        }
        RequestContext.getCurrentInstance().update("tempDialog:valorPorCotacaoBVData");
    }

    /**
     * Gera as baixas para o recebimento do valor a vista.
     *
     * @throws br.com.onesystem.exception.DadoInvalidoException
     */
    public BigDecimal getTotalConvertidoRecebido() {
        BigDecimal total = BigDecimal.ZERO;
        for (ValorPorCotacaoBV c : cotacoes) {
            total = total.add(c.getValorConvertidoRecebido());
        }
        return total;
    }

    public String getTotalConvertidoRecebidoFormatado() {
        Moeda moeda = buscarMoeda();
        return MoedaFormatter.format(moeda, getTotalConvertidoRecebido());

    }

    public Moeda buscarMoeda() {
        try {
            if (nota != null) {
                return nota.getCotacao().getConta().getMoeda();
            } else if (fatura != null) {
                return fatura.getMoedaPadrao();
            } else if (conhecimentoDeFrete != null) {
                return conhecimentoDeFrete.getMoedaPadrao();
            } else if (movimento != null) {
                return movimento.getCotacaoPadrao().getConta().getMoeda();
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
        return null;
    }

    public void finalizar() {
        try {
            if (nota != null) {
                for (ValorPorCotacaoBV c : cotacoes) {
                    constroiNota(c);
                }
                RequestContext.getCurrentInstance().closeDialog(nota);
                return;
            } else {
                RequestContext.getCurrentInstance().closeDialog(constroiListValorPorCotacao());
                return;
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void constroiNota(ValorPorCotacaoBV c) throws DadoInvalidoException {
        boolean entrou = false;
        for (ValorPorCotacao v : nota.getValorPorCotacao()) {
            if (c.getCotacao().getConta().getMoeda().equals(v.getCotacao().getConta().getMoeda())) {
                nota.atualiza(c.construirComId());
                entrou = true;
                break;
            }
        }
        if (!entrou && c.getValorAReceber().compareTo(BigDecimal.ZERO) > 0) {
            nota.adiciona(c.construir());
        }
    }

    private List<ValorPorCotacao> constroiListValorPorCotacao() throws DadoInvalidoException {
        List<ValorPorCotacao> list = new ArrayList<>();
        for (ValorPorCotacaoBV v : cotacoes) {
            if (v.getValorAReceber().compareTo(BigDecimal.ZERO) > 0) {
                list.add(v.construir());
            }
        }
        return list;
    }

    public void abrirDialogo() {
        exibeNaTela();
    }

    private void exibeNaTela() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("resizable", false);
        opcoes.put("width", 900);
        opcoes.put("draggable", true);
        opcoes.put("height", 400);
        opcoes.put("closable", true);
        opcoes.put("contentWidth", "100%");
        opcoes.put("contentHeight", "100%");
        opcoes.put("headerElement", "customheader");

        RequestContext.getCurrentInstance().openDialog("/dialogo/dialogoCotacao", opcoes, null);
    }

    public List<ModalidadeDeCobranca> getModalidadesDeCobranca() {
        return Arrays.asList(ModalidadeDeCobranca.values());
    }

    @Override
    public void limparJanela() {
        cotacoes = new ArrayList<>();
        nota = null;
        fatura = null;
        movimento = null;
        conhecimentoDeFrete = null;
        dinheiro = BigDecimal.ZERO;
    }

    @Override
    public void selecionar(SelectEvent event) {
    }

    public List<ValorPorCotacaoBV> getCotacoes() {
        return cotacoes;
    }

    public void setCotacoes(List<ValorPorCotacaoBV> cotacoes) {
        this.cotacoes = cotacoes;
    }

    public Nota getNota() {
        return nota;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
    }

}
