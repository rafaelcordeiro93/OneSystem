package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Grupo;
import br.com.onesystem.domain.builder.GrupoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;

public class GrupoBV implements Serializable {

    private Long id;
    private String nome;  

    public GrupoBV(Grupo grupoSelecionada) {
        this.id = grupoSelecionada.getId();
        this.nome = grupoSelecionada.getNome();
    }

    public GrupoBV() {
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

    public Grupo construir() throws DadoInvalidoException {
        return new GrupoBuilder().comNome(nome).construir();
    }

    public Grupo construirComID() throws DadoInvalidoException {
        return new GrupoBuilder().comID(id).comNome(nome).construir();
    }
}
