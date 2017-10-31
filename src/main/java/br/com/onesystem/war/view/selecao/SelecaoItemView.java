package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Item;
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
public class SelecaoItemView extends BasicCrudMBImpl<Item> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private ArmazemDeRegistros<Item> armazem;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = armazem.daClasse(Item.class, manager).listaTodosOsRegistros();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("estoque/selecao/selecaoItem");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/estoque/item";
    }

    @Override
    public List<Item> complete(String query) {
        buscarDados();
        List<Item> listaFiltrada = new ArrayList<>();
        for (Item b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFiltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Item m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFiltrada.add(m);
                }
            }
        }
        return listaFiltrada;
    }

    public String getIcon() {
        return "fa-archive";
    }
}
