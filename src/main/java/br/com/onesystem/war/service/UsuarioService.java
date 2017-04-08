package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.UsuarioDAO;
import br.com.onesystem.domain.Usuario;
import br.com.onesystem.war.util.UsuarioLogadoUtil;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "usuarioService")
@ApplicationScoped
public class UsuarioService implements Serializable {

    public List<Usuario> buscarUsuarios() {
        return new ArmazemDeRegistros<Usuario>(Usuario.class).listaTodosOsRegistros();
    }

    public Usuario buscarUsuarioPerfil() {
        UsuarioLogadoUtil user = new UsuarioLogadoUtil();
        if (user.getEmailUsuario() != null) {
            return new UsuarioDAO().buscarUsuarios().porEmailString(user.getEmailUsuario()).resultado();
        }
        else return null;
    }

}
