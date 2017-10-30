package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.builder.MoedaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import br.com.onesystem.valueobjects.TipoBandeira;
import java.io.Serializable;

public class MoedaBV implements Serializable, BuilderView<Moeda> {

    private Long id;
    private String nome;
    private String sigla;
    private TipoBandeira bandeira;
    private String nomePlural;
    private String nomeCasasDecimais;
    private String nomeCasasDecimaisPlural;

    public MoedaBV(Moeda moedaSelecionada) {
        this.id = moedaSelecionada.getId();
        this.nome = moedaSelecionada.getNome();
        this.sigla = moedaSelecionada.getSigla();
        this.bandeira = moedaSelecionada.getBandeira();
        this.nomePlural = moedaSelecionada.getNomePlural();
        this.nomeCasasDecimais = moedaSelecionada.getNomeCasasDecimais();
        this.nomeCasasDecimaisPlural = moedaSelecionada.getNomeCasasDecimaisPlural();
    }

    public MoedaBV() {
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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public TipoBandeira getBandeira() {
        return bandeira;
    }

    public void setBandeira(TipoBandeira bandeira) {
        this.bandeira = bandeira;
    }

    public String getNomePlural() {
        return nomePlural;
    }

    public void setNomePlural(String nomePlural) {
        this.nomePlural = nomePlural;
    }

    public String getNomeCasasDecimais() {
        return nomeCasasDecimais;
    }

    public void setNomeCasasDecimais(String nomeCasasDecimais) {
        this.nomeCasasDecimais = nomeCasasDecimais;
    }

    public String getNomeCasasDecimaisPlural() {
        return nomeCasasDecimaisPlural;
    }

    public void setNomeCasasDecimaisPlural(String nomeCasasDecimaisPlural) {
        this.nomeCasasDecimaisPlural = nomeCasasDecimaisPlural;
    }

    public Moeda construir() throws DadoInvalidoException {
        return new MoedaBuilder().comNome(nome).comNomePlural(nomePlural).comSigla(sigla).comBandeira(bandeira)
                .comNomeCasasDecimais(nomeCasasDecimais).comNomeCasasDecimaisPlural(nomeCasasDecimaisPlural).construir();
    }

    public Moeda construirComID() throws DadoInvalidoException {
        return new MoedaBuilder().comID(id).comNome(nome).comNomePlural(nomePlural).comSigla(sigla).comBandeira(bandeira)
                .comNomeCasasDecimais(nomeCasasDecimais).comNomeCasasDecimaisPlural(nomeCasasDecimaisPlural).construir();
    }
}
