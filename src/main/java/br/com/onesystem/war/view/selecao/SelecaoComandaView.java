package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Comanda;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.ComandaService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoComandaView extends BasicCrudMBImpl<Comanda> implements Serializable {

    @Inject
    private ComandaService service;

    @PostConstruct
    public void init() {
        beans = service.buscarComandas();
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

    public ComandaService getService() {
        return service;
    }

    public void setService(ComandaService service) {
        this.service = service;
    }
}
