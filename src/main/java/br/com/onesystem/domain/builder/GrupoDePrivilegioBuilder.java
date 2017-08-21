package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.GrupoDePrivilegio;

public class GrupoDePrivilegioBuilder {

    private Long id;
    private String nome;

    public GrupoDePrivilegioBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public GrupoDePrivilegioBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public GrupoDePrivilegio contruir() {
        return new GrupoDePrivilegio(id, nome);
    }

}
