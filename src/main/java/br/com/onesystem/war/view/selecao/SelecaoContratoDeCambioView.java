package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.ContratoDeCambioDAO;
import br.com.onesystem.domain.ContratoDeCambio;
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
public class SelecaoContratoDeCambioView extends BasicCrudMBImpl<ContratoDeCambio> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private ContratoDeCambioDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("/cambio/selecao/selecaoContratoDeCambio");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/cambio/contratoDeCambio";
    }

    @Override
    public List<ContratoDeCambio> complete(String query) {
        buscarDados();
        List<ContratoDeCambio> listaFIltrada = new ArrayList<>();
        if (!StringUtils.containsLetter(query)) {
            for (ContratoDeCambio m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

}
