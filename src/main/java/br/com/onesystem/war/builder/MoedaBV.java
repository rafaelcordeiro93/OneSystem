package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.builder.MoedaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;

public class MoedaBV implements Serializable {

    private Long id;
    private String nome;   
    private String sigla;

    public MoedaBV(Moeda moedaSelecionada) {
        this.id = moedaSelecionada.getId();
        this.nome = moedaSelecionada.getNome();
        this.sigla = moedaSelecionada.getSigla();
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

    public Moeda construir() throws DadoInvalidoException {
        return new MoedaBuilder().comNome(nome).comSigla(sigla).construir();
    }

    public Moeda construirComID() throws DadoInvalidoException {
        return new MoedaBuilder().comID(id).comNome(nome).comSigla(sigla).construir();
    }
}
