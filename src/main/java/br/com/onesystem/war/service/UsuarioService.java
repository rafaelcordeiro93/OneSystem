package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Usuario;
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

}
