package br.com.onesystem.war.view.dialogo;

import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.ValorPorCotacao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.reportTemplate.ValorPorCotacaoBV;
import br.com.onesystem.util.MoedaFomatter;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.valueobjects.ModalidadeDeCobranca;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@RequestScoped
public class DialogoCotacaoView extends BasicMBImpl<ValorPorCotacao, ValorPorCotacaoBV> implements Serializable {

    private List<ValorPorCotacaoBV> cotacoes;
    private Nota nota;

    @PostConstruct
    public void init() {
        try {
            limparJanela();
            buscaDaSessao();
            if (nota != null) {
                inicializaCotacoes();
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void buscaDaSessao() throws DadoInvalidoException {
        nota = (Nota) SessionUtil.getObject("nota", FacesContext.getCurrentInstance());
    }

    private void inicializaCotacoes() {
        List<Cotacao> cotacaoLista = new CotacaoDAO().buscarCotacoes().naMaiorEmissao(nota.getEmissao()).listaDeResultados();
        cotacoes = new ArrayList<>();
        for (Cotacao c : cotacaoLista) {
            cotacoes.add(new ValorPorCotacaoBV(c, null, null, null, nota.getMoedaPadrao()));
        }
        calculaCotacoes();
    }

    /**
     * Busca a moeda padrão e abre a janela de cotações com o valor restante na
     * cotação de cada moeda.
     */
    public void calculaCotacoes() {
        RequestContext rc = RequestContext.getCurrentInstance();
        for (ValorPorCotacaoBV c : cotacoes) {
            c.setTotal(nota.getTotalEmDinheiro());
            c.setTotalConvertidoRecebido(getTotalConvertidoRecebido());
        }
        rc.update("tempDialog:valorPorCotacaoBVData");
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
        return MoedaFomatter.format(nota.getMoedaPadrao(), getTotalConvertidoRecebido());
    }

    public void finalizar() throws DadoInvalidoException {
        for (ValorPorCotacaoBV c : cotacoes) {
            boolean entrou = false;
            for (ValorPorCotacao v : nota.getValorPorCotacao()) {
                if (c.getId().equals(v.getId())) {
                    nota.atualiza(c.construirComId());
                    entrou = true;
                    break;
                }
            }
            if (!entrou && c.getValorAReceber().compareTo(BigDecimal.ZERO) > 0) {
                nota.adiciona(c.construir());
            }
        }
        RequestContext.getCurrentInstance().closeDialog(nota);
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

        RequestContext.getCurrentInstance().openDialog("dialogo/dialogoCotacao", opcoes, null);
    }

    public List<ModalidadeDeCobranca> getModalidadesDeCobranca() {
        return Arrays.asList(ModalidadeDeCobranca.values());
    }

    @Override
    public void limparJanela() {
        cotacoes = new ArrayList<>();
        nota = null;
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
