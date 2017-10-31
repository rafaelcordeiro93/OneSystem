package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.EstadoDAO;
import br.com.onesystem.domain.Estado;
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
public class SelecaoEstadoView extends BasicCrudMBImpl<Estado> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private EstadoDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("arquivo/selecao/selecaoEstado");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/arquivo/estado";
    }

    @Override
    public List<Estado> complete(String query) {
        buscarDados();
        List<Estado> listaFIltrada = new ArrayList<>();
        for (Estado b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Estado m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public String getIcon() {
        return "fa-flag-checkered";
    }
}
