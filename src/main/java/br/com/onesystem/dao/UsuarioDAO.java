/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Usuario;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.JPAUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Rafael-Pc
 */
public class UsuarioDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public UsuarioDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public UsuarioDAO buscarUsuarios() {
        consulta += "select u from Usuario u where u.id > 0 ";
        return this;
    }

    public UsuarioDAO porNome(Usuario usuario) {
        consulta += " and u.pessoa.nome = :uNome ";
        parametros.put("uNome", usuario.getPessoa().getNome());
        return this;
    }

    public UsuarioDAO porEmail(Usuario usuario) {
        consulta += " and u.pessoa.email = :uEmail ";
        parametros.put("uEmail", usuario.getPessoa().getEmail());
        return this;
    }

    public UsuarioDAO porEmailString(String email) {
        consulta += " and u.pessoa.email = :uEmail ";
        parametros.put("uEmail", email);
        return this;
    }

    public UsuarioDAO porId(Long id) {
        consulta += " and u.id = :uId ";
        parametros.put("uId", id);
        return this;
    }

    public UsuarioDAO porPessoa(Pessoa pessoa) {
        consulta += " and u.pessoa.id = :uId ";
        parametros.put("uId", pessoa.getId());
        return this;
    }

    public String getFirstNameLastNameUserByEmail(String email) {
        EntityManager manager = JPAUtil.getEntityManager();

        TypedQuery<Usuario> query = manager.createQuery("select u from Usuario u where u.email = :pEmail ",
                Usuario.class);

        query.setParameter("pEmail", email);
        Usuario usuario = new Usuario();
        usuario = query.getSingleResult();

        return usuario.getPessoa().getFirstNameLastName();
    }

    public List<Usuario> listaDeResultados() {
        List<Usuario> resultado = new ArmazemDeRegistros<Usuario>(Usuario.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public Usuario resultado() {
        Usuario resultado = new ArmazemDeRegistros<Usuario>(Usuario.class)
                .resultadoUnicoDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

}
