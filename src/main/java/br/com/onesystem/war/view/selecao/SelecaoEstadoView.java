package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Estado;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.EstadoService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoEstadoView extends BasicCrudMBImpl<Estado> implements Serializable {

    @Inject
    private EstadoService service;

    @PostConstruct
    public void init() {
        beans = service.buscarEstados();
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

    public EstadoService getService() {
        return service;
    }

    public void setService(EstadoService service) {
        this.service = service;
    }

    public String getIcon() {
        return "fa-flag-checkered";
    }
}
