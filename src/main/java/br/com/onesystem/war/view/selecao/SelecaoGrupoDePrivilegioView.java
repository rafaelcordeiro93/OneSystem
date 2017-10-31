package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.GrupoDePrivilegioDAO;
import br.com.onesystem.domain.GrupoDePrivilegio;
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
public class SelecaoGrupoDePrivilegioView extends BasicCrudMBImpl<GrupoDePrivilegio> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private GrupoDePrivilegioDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("topbar/preferencias/selecao/selecaoGrupoPrivilegio");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/topbar/preferencias/grupoPrivilegio";
    }

    @Override
    public List<GrupoDePrivilegio> complete(String query) {
        buscarDados();
        List<GrupoDePrivilegio> listaFIltrada = new ArrayList<>();
        for (GrupoDePrivilegio b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (GrupoDePrivilegio m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

}
