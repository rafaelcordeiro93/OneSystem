package br.com.onesystem.war.builder;

import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.domain.Usuario;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;

public class UsuarioBV implements Serializable {

    private Long id;
    private String senha;
    private Pessoa pessoa;
    private GrupoDePrivilegio grupoPrivilegio;
    private boolean supervisor;

    public UsuarioBV(Usuario usuarioSelecionada) {
        this.id = usuarioSelecionada.getId();
        this.pessoa = usuarioSelecionada.getPessoa();
        this.grupoPrivilegio = usuarioSelecionada.getGrupoDePrivilegio();
        this.senha = usuarioSelecionada.getSenha();
        this.supervisor = usuarioSelecionada.isSupervisor();
    }

    public UsuarioBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public GrupoDePrivilegio getGrupoPrivilegio() {
        return grupoPrivilegio;
    }

    public void setGrupoPrivilegio(GrupoDePrivilegio grupoPrivilegio) {
        this.grupoPrivilegio = grupoPrivilegio;
    }

    public boolean isSupervisor() {
        return supervisor;
    }

    public void setSupervisor(boolean supervisor) {
        this.supervisor = supervisor;
    }

    public Usuario construir() throws DadoInvalidoException {
        return new Usuario(null, pessoa, senha, grupoPrivilegio, supervisor);
    }

    public Usuario construirComID() throws DadoInvalidoException {
        return new Usuario(id, pessoa, senha, grupoPrivilegio, supervisor);
    }
}
