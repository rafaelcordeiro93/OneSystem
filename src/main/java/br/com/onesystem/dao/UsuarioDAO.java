/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.Usuario;
import br.com.onesystem.util.BundleUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.hibernate.Hibernate;

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

    public List<Usuario> listaDeResultados() {
        List<Usuario> resultado = new ArmazemDeRegistros<Usuario>(Usuario.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

}
