package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.UnidadeMedidaItemService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoUnidadeMedidaItemView extends BasicCrudMBImpl<UnidadeMedidaItem> implements Serializable {

    @ManagedProperty("#{unidadeMedidaItemService}")
    private UnidadeMedidaItemService service;

    @PostConstruct
    public void init() {
        beans = service.buscarUnidadeMedidaItens();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoUnidadeMedidaItem");
    }

    @Override
    public String abrirEdicao(){
        return "unidadeMedidaItem";
    }
    
     @Override
    public List<UnidadeMedidaItem> complete(String query) {
        List<UnidadeMedidaItem> unidadeFIltrada = new ArrayList<>();
        for (UnidadeMedidaItem u : beans) {
            if (StringUtils.startsWithIgnoreCase(u.getNome(), query)) {
                unidadeFIltrada.add(u);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (UnidadeMedidaItem u : beans) {
                if (StringUtils.startsWithIgnoreCase(u.getId().toString(), query)) {
                    unidadeFIltrada.add(u);
                }
            }
        }
        return unidadeFIltrada;
    }
    
    public UnidadeMedidaItemService getService() {
        return service;
    }

    public void setService(UnidadeMedidaItemService service) {
        this.service = service;
    }
}
