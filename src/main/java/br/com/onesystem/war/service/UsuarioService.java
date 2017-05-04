package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.UsuarioDAO;
import br.com.onesystem.domain.Usuario;
import br.com.onesystem.war.util.UsuarioLogadoUtil;
import java.io.Serializable;
import java.util.List;

public class UsuarioService implements Serializable {

    public List<Usuario> buscarUsuarios() {
        return new ArmazemDeRegistros<Usuario>(Usuario.class).listaTodosOsRegistros();
    }

    public Usuario buscarUsuarioPerfil() {
       String user = new UsuarioLogadoUtil().getEmailUsuario();
        if (user != null) {
            return new UsuarioDAO().buscarUsuarios().porEmailString(user).resultado();
        } 
        else return null;
    }

}
