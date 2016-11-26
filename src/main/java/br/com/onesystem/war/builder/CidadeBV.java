package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Cidade;
import br.com.onesystem.domain.builder.CidadeBuilder;
import br.com.onesystem.services.CharacterType;
import br.com.onesystem.valueobjects.CaseType;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class CidadeBV implements Serializable {

    private Long id;
    private String nome;   
    private String UF;
    private String pais;

    public CidadeBV(Cidade cidadeSelecionada) {
        this.id = cidadeSelecionada.getId();
        this.nome = cidadeSelecionada.getNome();
        this.UF = cidadeSelecionada.getUf();
        this.pais = cidadeSelecionada.getPais();
    }

    public CidadeBV() {
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

    public String getUF() {
        return UF;
    }

    public void setUF(String UF) {
        this.UF = UF;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Cidade construir() throws DadoInvalidoException {
        return new CidadeBuilder().comNome(nome).comUF(UF).comPais(pais).construir();
    }

    public Cidade construirComID() throws DadoInvalidoException {
        return new CidadeBuilder().comID(id).comNome(nome).comUF(UF).comPais(pais).construir();
    }
}
