package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Filial;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.FilialService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoFilialView extends BasicCrudMBImpl<Filial> implements Serializable {

    @Inject
    private FilialService service;

    @PostConstruct
    public void init() {
        beans = service.buscarFiliais();
    }

    public void abrirDialogo() {
        exibirNaTela("topbar/preferencias/selecao/selecaoFilial");
    }
    
    @Override
    public String abrirEdicao() {
        return "/menu/topbar/preferencias/filial";
    }
    
    @Override
    public List<Filial> complete(String query) {
        List<Filial> listaFIltrada = new ArrayList<>();
        for (Filial b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Filial m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public FilialService getService() {
        return service;
    }

    public void setService(FilialService service) {
        this.service = service;
    }
}
