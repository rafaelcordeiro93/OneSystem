package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Pais;
import br.com.onesystem.domain.Pais;
import br.com.onesystem.domain.builder.PaisBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;

public class PaisBV implements Serializable, BuilderView<Pais> {

    private Long id;
    private String nome;
    private Long codigoPais;
    private Long codigoReceita;

    public PaisBV(Pais pais) {
        this.id = pais.getId();
        this.nome = pais.getNome();
        this.codigoPais = pais.getCodigoPais();
        this.codigoReceita = pais.getCodigoReceita();
    }

    public PaisBV() {
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

    public Long getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(Long codigoPais) {
        this.codigoPais = codigoPais;
    }

    public Long getCodigoReceita() {
        return codigoReceita;
    }

    public void setCodigoReceita(Long codigoReceita) {
        this.codigoReceita = codigoReceita;
    }

    public Pais construir() throws DadoInvalidoException {
        return new PaisBuilder().comNome(nome).comCodigoPais(codigoPais).comCodigoReceita(codigoReceita).construir();
    }

    public Pais construirComID() throws DadoInvalidoException {
        return new PaisBuilder().comNome(nome).comCodigoPais(codigoPais).comCodigoReceita(codigoReceita).construir();
    }
}
