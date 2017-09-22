package br.com.onesystem.war.service;

import br.com.onesystem.dao.UsuarioDAO;
import br.com.onesystem.domain.Usuario;
import br.com.onesystem.util.UsuarioLogadoUtil;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class UsuarioService implements Serializable {

    @Inject
    private UsuarioDAO dao;

    @Inject
    private UsuarioLogadoUtil usuarioLogado;

    public List<Usuario> buscarUsuarios() {
        return dao.listaDeResultados();
    }

    public Usuario buscarUsuarioPerfil() {
        String user = usuarioLogado.getEmailUsuario();
        if (user != null) {
            return dao.porEmailString(user).resultado();
        } else {
            return null;
        }
    }

}
