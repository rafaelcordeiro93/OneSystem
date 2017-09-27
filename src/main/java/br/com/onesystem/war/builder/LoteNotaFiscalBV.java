package br.com.onesystem.war.builder;

import br.com.onesystem.domain.LoteNotaFiscal;
import br.com.onesystem.domain.NumeracaoDeNotaFiscal;
import br.com.onesystem.domain.builder.LoteNotaFiscalBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import br.com.onesystem.valueobjects.EspecieDeNotaFiscal;
import br.com.onesystem.valueobjects.ModeloDeNotaFiscal;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class LoteNotaFiscalBV implements Serializable, BuilderView<LoteNotaFiscal> {

    private Long id;
    private String nome;
    private ModeloDeNotaFiscal modelo;
    private EspecieDeNotaFiscal especie;
    private Long serie;
    private Date dataDeInicio;
    private Date dataDeFim;
    private String observacao;
    private List<NumeracaoDeNotaFiscal> numeracaoDeNotaFiscal;

    public LoteNotaFiscalBV(LoteNotaFiscal n) {
        this.id = n.getId();
        this.nome = n.getNome();
        this.modelo = n.getModelo();
        this.especie = n.getEspecie();
        this.serie = n.getSerie();
        this.dataDeFim = n.getDataDeFim();
        this.dataDeInicio = n.getDataDeInicio();
        this.observacao = n.getObservacao();
        this.numeracaoDeNotaFiscal = n.getNumeracaoDeNotaFiscal();
    }

    public LoteNotaFiscalBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ModeloDeNotaFiscal getModelo() {
        return modelo;
    }

    public void setModelo(ModeloDeNotaFiscal modelo) {
        this.modelo = modelo;
    }

    public EspecieDeNotaFiscal getEspecie() {
        return especie;
    }

    public void setEspecie(EspecieDeNotaFiscal especie) {
        this.especie = especie;
    }

    public Long getSerie() {
        return serie;
    }

    public void setSerie(Long serie) {
        this.serie = serie;
    }

    public Date getDataDeInicio() {
        return dataDeInicio;
    }

    public void setDataDeInicio(Date dataDeInicio) {
        this.dataDeInicio = dataDeInicio;
    }

    public Date getDataDeFim() {
        return dataDeFim;
    }

    public void setDataDeFim(Date dataDeFim) {
        this.dataDeFim = dataDeFim;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public List<NumeracaoDeNotaFiscal> getNumeracaoDeNotaFiscal() {
        return numeracaoDeNotaFiscal;
    }

    public void setNumeracaoDeNotaFiscal(List<NumeracaoDeNotaFiscal> numeracaoDeNotaFiscal) {
        this.numeracaoDeNotaFiscal = numeracaoDeNotaFiscal;
    }

    public LoteNotaFiscal construir() throws DadoInvalidoException {
        return new LoteNotaFiscalBuilder().comNome(nome).comModelo(modelo).comEspecie(especie)
                .comSerie(serie).comDataDeFim(dataDeFim).comDataDeInicio(dataDeInicio).comObservacao(observacao)
                .comNumeracaoDeNotaFiscal(numeracaoDeNotaFiscal).construir();
    }

    public LoteNotaFiscal construirComID() throws DadoInvalidoException {
        return new LoteNotaFiscalBuilder().comId(id).comNome(nome).comModelo(modelo).comEspecie(especie)
                .comSerie(serie).comDataDeFim(dataDeFim).comDataDeInicio(dataDeInicio).comObservacao(observacao)
                .comNumeracaoDeNotaFiscal(numeracaoDeNotaFiscal).construir();
    }
}
