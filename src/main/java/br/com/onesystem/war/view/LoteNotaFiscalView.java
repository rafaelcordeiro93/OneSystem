package br.com.onesystem.war.view;

import br.com.onesystem.domain.LoteNotaFiscal;
import br.com.onesystem.domain.NumeracaoDeNotaFiscal;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.Model;
import br.com.onesystem.util.ModelList;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.valueobjects.EspecieDeNotaFiscal;
import br.com.onesystem.valueobjects.ModeloDeNotaFiscal;
import br.com.onesystem.war.builder.LoteNotaFiscalBV;
import br.com.onesystem.war.builder.NumeracaoDeNotaFiscalBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import br.com.onesystem.war.view.dialogo.DialogoCobrancaView;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class LoteNotaFiscalView extends BasicMBImpl<LoteNotaFiscal, LoteNotaFiscalBV> implements Serializable {

    private NumeracaoDeNotaFiscalBV numeracaoNF;
    private Model<NumeracaoDeNotaFiscal> numeracaoSelecionado;
    private ModelList<NumeracaoDeNotaFiscal> listaNumeracao;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void limparJanela() {
        e = new LoteNotaFiscalBV();
        numeracaoNF = new NumeracaoDeNotaFiscalBV();
        numeracaoSelecionado = null;
        listaNumeracao = new ModelList<>();
    }

    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof LoteNotaFiscal) {
            e = new LoteNotaFiscalBV((LoteNotaFiscal) event.getObject());
        }
    }

    public void addNumeracao() {
        try {
            listaNumeracao.add(numeracaoNF.construir());
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }
    
    public void updateNumeracao() {
        try {
            listaNumeracao.add(numeracaoNF.construir());
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void removeNumeracao() {
        listaNumeracao.remove(numeracaoSelecionado);
        numeracaoSelecionado = null;
    }

    public List<ModeloDeNotaFiscal> getModelosDeNotaFiscal() {
        return Arrays.asList(ModeloDeNotaFiscal.values());
    }

    public List<EspecieDeNotaFiscal> getEspeciesDeNotaFiscal() {
        return Arrays.asList(EspecieDeNotaFiscal.values());
    }

    public NumeracaoDeNotaFiscalBV getNumeracaoNF() {
        return numeracaoNF;
    }

    public void setNumeracaoNF(NumeracaoDeNotaFiscalBV numeracaoNF) {
        this.numeracaoNF = numeracaoNF;
    }

    public Model<NumeracaoDeNotaFiscal> getNumeracaoSelecionado() {
        return numeracaoSelecionado;
    }

    public void setNumeracaoSelecionado(Model<NumeracaoDeNotaFiscal> numeracaoSelecionado) {
        this.numeracaoSelecionado = numeracaoSelecionado;
    }

    public ModelList<NumeracaoDeNotaFiscal> getListaNumeracao() {
        return listaNumeracao;
    }

    public void setListaNumeracao(ModelList<NumeracaoDeNotaFiscal> listaNumeracao) {
        this.listaNumeracao = listaNumeracao;
    }

}
