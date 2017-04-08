package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Usuario;
import br.com.onesystem.war.service.UsuarioService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoUsuarioView extends BasicCrudMBImpl<Usuario> implements Serializable {

    @ManagedProperty("#{usuarioService}")
    private UsuarioService service;

    @PostConstruct
    public void init() {
        beans = service.buscarUsuarios();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("selecaoUsuario");
    }

    public UsuarioService getService() {
        return service;
    }

    public void setService(UsuarioService service) {
        this.service = service;
    }

}
