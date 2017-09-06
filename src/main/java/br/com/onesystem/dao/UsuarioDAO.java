/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Usuario;

/**
 *
 * @author Rafael-Pc
 */
public class UsuarioDAO extends GenericDAO<Usuario> {

    public UsuarioDAO() {
        super(Usuario.class);
    }

    public UsuarioDAO porNome(Usuario usuario) {
        where += " and usuario.pessoa.nome = :uNome ";
        parametros.put("uNome", usuario.getPessoa().getNome());
        return this;
    }

    public UsuarioDAO porEmail(Usuario usuario) {
        where += " and usuario.pessoa.email = :uEmail ";
        parametros.put("uEmail", usuario.getPessoa().getEmail());
        return this;
    }

    public UsuarioDAO porEmailString(String email) {
        where += " and usuario.pessoa.email = :uEmail ";
        parametros.put("uEmail", email);
        return this;
    }

    public UsuarioDAO porId(Long id) {
        where += " and usuario.id = :uId ";
        parametros.put("uId", id);
        return this;
    }

    public UsuarioDAO porPessoa(Pessoa pessoa) {
        where += " and usuario.pessoa.id = :uId ";
        parametros.put("uId", pessoa.getId());
        return this;
    }

}
