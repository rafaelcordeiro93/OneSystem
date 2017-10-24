package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.ItemDAO;
import br.com.onesystem.domain.Item;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.ItemService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class SelecaoItemView extends BasicCrudMBImpl<Item> implements Serializable {

//    @Inject
//    private ItemDAO dao;
    @Inject
    private ItemService service;

    @PostConstruct
    public void init() {
        buscarItens();
    }

    public void buscarItens() {
        beans = service.buscarItems();
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
        buscarItens();
        List<Item> listaFIltrada = new ArrayList<>();
        for (Item b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Item m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public ItemService getService() {
        return service;
    }

    public void setService(ItemService service) {
        this.service = service;
    }

    public String getIcon() {
        return "fa-archive";
    }
}
