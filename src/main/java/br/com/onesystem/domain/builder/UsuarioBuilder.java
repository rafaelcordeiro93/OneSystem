package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Usuario;
import br.com.onesystem.exception.DadoInvalidoException;

/**
 *
 * @author Rafael
 */
public class UsuarioBuilder {

    private Long ID;
    private Pessoa pessoa;
    private String senha;
    private GrupoDePrivilegio grupoPrivilegio;
    private boolean supervisor = false;

    public UsuarioBuilder comID(Long ID) {
        this.ID = ID;
        return this;
    }

    public UsuarioBuilder comPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public UsuarioBuilder comSenha(String senha) {
        this.senha = senha;
        return this;
    }

    public UsuarioBuilder comGrupoDePrevilegio(GrupoDePrivilegio grupoPrivilegio) {
        this.grupoPrivilegio = grupoPrivilegio;
        return this;
    }

    public UsuarioBuilder comPessoa(boolean supervisor) {
        this.supervisor = supervisor;
        return this;
    }

    public Usuario construir() throws DadoInvalidoException {
        return new Usuario(ID, pessoa, senha, grupoPrivilegio, supervisor);
    }

}
