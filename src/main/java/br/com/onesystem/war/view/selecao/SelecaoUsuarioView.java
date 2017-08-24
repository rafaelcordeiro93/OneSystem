package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Usuario;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.UsuarioService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoUsuarioView extends BasicCrudMBImpl<Usuario> implements Serializable {

    @Inject
    private UsuarioService service;

    @PostConstruct
    public void init() {
        beans = service.buscarUsuarios();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("topbar/preferencias/selecao/selecaoUsuario");
    }

    @Override
    public String abrirEdicao() {
            return "/menu/topbar/preferencias/usuario";
    }

    @Override
    public List<Usuario> complete(String query) {
        List<Usuario> moedasFIltradas = new ArrayList<>();
        for (Usuario m : beans) {
            if (StringUtils.startsWithIgnoreCase(m.getPessoa().getEmail(), query)) {
                moedasFIltradas.add(m);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Usuario m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    moedasFIltradas.add(m);
                }
            }
        }
        return moedasFIltradas;
    }

    public UsuarioService getService() {
        return service;
    }

    public void setService(UsuarioService service) {
        this.service = service;
    }

}
