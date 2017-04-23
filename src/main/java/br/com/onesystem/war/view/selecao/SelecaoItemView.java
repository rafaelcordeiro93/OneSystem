package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Item;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.ItemService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoItemView extends BasicCrudMBImpl<Item> implements Serializable {

    @Inject
    private ItemService service;

    @PostConstruct
    public void init() {
        beans = service.buscarItems();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("selecaoItem");
    }
    
    @Override
    public String abrirEdicao() {
        return "item";
    }
    
    @Override
    public List<Item> complete(String query) {
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
}
