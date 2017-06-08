package br.com.onesystem.war.builder;

import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.domain.builder.GrupoDePrivilegioBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;

public class GrupoDePrivilegioBV implements Serializable, BuilderView<GrupoDePrivilegio> {

    private Long id;
    private String nome;

    public GrupoDePrivilegioBV() {
    }

    public GrupoDePrivilegioBV(GrupoDePrivilegio grupo) {
        this.id = grupo.getId();
        this.nome = grupo.getNome();
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

    public GrupoDePrivilegio construir() {
        return new GrupoDePrivilegioBuilder().comNome(nome).contruir();
    }

    @Override
    public GrupoDePrivilegio construirComID() throws DadoInvalidoException {
        return new GrupoDePrivilegioBuilder().comId(id).comNome(nome).contruir();
    }

}
