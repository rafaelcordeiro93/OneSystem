package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.LoteItem;
import br.com.onesystem.domain.LoteItem;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.LoteItemService;
import br.com.onesystem.war.service.LoteItemService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoLoteItemView extends BasicCrudMBImpl<LoteItem> implements Serializable {

    @Inject
    private LoteItemService service;

    @PostConstruct
    public void init() {
        beans = service.buscarLoteItem();
    }

    public void abrirDialogo() {
        exibirNaTela("estoque/selecao/selecaoLoteItem");
    }
    
    @Override
    public String abrirEdicao() {
        return "/menu/estoque/item";
    }
    
    @Override
    public List<LoteItem> complete(String query) {
        List<LoteItem> moedasFIltradas = new ArrayList<>();
        for (LoteItem m : beans) {
            if (StringUtils.startsWithIgnoreCase(m.getNumeroDoLote().toString(), query)) {
                moedasFIltradas.add(m);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (LoteItem m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    moedasFIltradas.add(m);
                }
            }
        }
        return moedasFIltradas;
    }


    public LoteItemService getService() {
        return service;
    }

    public void setService(LoteItemService service) {
        this.service = service;
    }
}
