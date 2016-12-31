package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.builder.MoedaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoBandeira;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class MoedaBV implements Serializable {

    private Long id;
    private String nome;
    private String sigla;
    private TipoBandeira bandeira;

    public MoedaBV(Moeda moedaSelecionada) {
        this.id = moedaSelecionada.getId();
        this.nome = moedaSelecionada.getNome();
        this.sigla = moedaSelecionada.getSigla();
        this.bandeira = moedaSelecionada.getBandeira();
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

    public Moeda construir() throws DadoInvalidoException {
        return new MoedaBuilder().comNome(nome).comSigla(sigla).comBandeira(bandeira).construir();
    }

    public Moeda construirComID() throws DadoInvalidoException {
        return new MoedaBuilder().comID(id).comNome(nome).comSigla(sigla).comBandeira(bandeira).construir();
    }
}
