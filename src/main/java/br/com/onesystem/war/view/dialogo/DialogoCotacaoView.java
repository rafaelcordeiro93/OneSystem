package br.com.onesystem.war.view.dialogo;

import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.ConhecimentoDeFrete;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.FaturaEmitida;
import br.com.onesystem.domain.FaturaRecebida;
import br.com.onesystem.domain.Moeda;
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
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class DialogoCotacaoView extends BasicMBImpl<ValorPorCotacao, ValorPorCotacaoBV> implements Serializable {

    private List<ValorPorCotacaoBV> cotacoes;
    private Nota nota;
    private FaturaEmitida faturaEmitida;
    private FaturaRecebida faturaRecebida;
    private ConhecimentoDeFrete conhecimentoDeFrete;
    private BigDecimal dinheiro;

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
     * @date
     *
     * Método utilizado para buscar os objetos da sessão
     */
    private void buscaDaSessao() throws DadoInvalidoException {
        // Rafael Cordeiro - 01/08/2017 - Adequação para as classes de Fatura Emitida e Recebida
        nota = (Nota) SessionUtil.getObject("nota", FacesContext.getCurrentInstance());
        if (nota != null) {
            inicializaCotacoes(nota.getEmissao(), nota.getMoedaPadrao());
            dinheiro = nota.getTotalEmDinheiro();
            calculaCotacoes();
            return;
        }
        faturaEmitida = (FaturaEmitida) SessionUtil.getObject("faturaEmitida", FacesContext.getCurrentInstance());
        if (faturaEmitida != null) {
            inicializaCotacoes(faturaEmitida.getEmissao(), faturaEmitida.getMoedaPadrao());
            dinheiro = faturaEmitida.getDinheiro();
            calculaCotacoes();
            return;
        }
        faturaRecebida = (FaturaRecebida) SessionUtil.getObject("faturaRecebida", FacesContext.getCurrentInstance());
        if (faturaRecebida != null) {
            inicializaCotacoes(faturaRecebida.getEmissao(), faturaRecebida.getMoedaPadrao());
            dinheiro = faturaRecebida.getDinheiro();
            calculaCotacoes();
            return;
        }
        conhecimentoDeFrete = (ConhecimentoDeFrete) SessionUtil.getObject("conhecimentoDeFrete", FacesContext.getCurrentInstance());
        if (conhecimentoDeFrete != null) {
            inicializaCotacoes(conhecimentoDeFrete.getEmissao(), conhecimentoDeFrete.getMoedaPadrao());
            dinheiro = conhecimentoDeFrete.getDinheiro();
            calculaCotacoes();
            return;
        }
    }

    private void inicializaCotacoes(Date emissao, Moeda moedaPadrao) {
        List<Cotacao> cotacaoLista = new CotacaoDAO().naMaiorEmissao(emissao).listaDeResultados();
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
                return nota.getMoedaPadrao();
            } else if (faturaEmitida != null) {
                return faturaEmitida.getMoedaPadrao();
            } else if (faturaRecebida != null) {
                return faturaRecebida.getMoedaPadrao();
            } else if (conhecimentoDeFrete != null) {
                return conhecimentoDeFrete.getMoedaPadrao();
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
            }
            if (faturaEmitida != null || faturaRecebida != null || conhecimentoDeFrete != null) {
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
        faturaEmitida = null;
        faturaRecebida = null;
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
