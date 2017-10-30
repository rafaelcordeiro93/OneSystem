package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.ComandaDAO;
import br.com.onesystem.domain.Comanda;
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
public class SelecaoComandaView extends BasicCrudMBImpl<Comanda> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private ComandaDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("vendas/selecao/selecaoComanda");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/vendas/comanda";
    }

    @Override
    public List<Comanda> complete(String query) {
        buscarDados();
        List<Comanda> comandasFiltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (Comanda c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    comandasFiltradas.add(c);
                }
            }
        }
        return comandasFiltradas;
    }

}
