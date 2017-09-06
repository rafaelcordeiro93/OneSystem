package br.com.onesystem.war.service;

import br.com.onesystem.dao.UsuarioDAO;
import br.com.onesystem.domain.Usuario;
import br.com.onesystem.util.UsuarioLogadoUtil;
import java.io.Serializable;
import java.util.List;

public class UsuarioService implements Serializable {

    public List<Usuario> buscarUsuarios() {
        return new UsuarioDAO().listaDeResultados();
    }

    public Usuario buscarUsuarioPerfil() {
        String user = new UsuarioLogadoUtil().getEmailUsuario();
        if (user != null) {
            return new UsuarioDAO().porEmailString(user).resultado();
        } else {
            return null;
        }
    }

}
