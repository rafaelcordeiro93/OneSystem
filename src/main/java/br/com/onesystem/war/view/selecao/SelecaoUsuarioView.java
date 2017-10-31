package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.UsuarioDAO;
import br.com.onesystem.domain.Usuario;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Named
@ViewScoped
public class SelecaoUsuarioView extends BasicCrudMBImpl<Usuario> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private UsuarioDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
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
        buscarDados();
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

}
