package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Grupo;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.GrupoService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoGrupoView extends BasicCrudMBImpl<Grupo> implements Serializable {

    @Inject
    private GrupoService service;

    @PostConstruct
    public void init() {
        beans = service.buscarGrupos();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoGrupo");
    }
    
    @Override
    public String abrirEdicao() {
        return "grupo";
    }
    
    @Override
    public List<Grupo> complete(String query) {
        List<Grupo> listaFIltrada = new ArrayList<>();
        for (Grupo b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Grupo m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public GrupoService getService() {
        return service;
    }

    public void setService(GrupoService service) {
        this.service = service;
    }
}
