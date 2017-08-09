package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.GrupoPrivilegioService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoGrupoDePrivilegioView extends BasicCrudMBImpl<GrupoDePrivilegio> implements Serializable {

    @Inject
    private GrupoPrivilegioService service;

    @PostConstruct
    public void init() {
        beans = service.buscarGrupoDePrivilegio();
    }

    public void abrirDialogo() {
        exibirNaTela("topbar/preferencias/selecao/selecaoGrupoPrivilegio");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/topbar/preferencias/grupoPrivilegio";
    }
    
    @Override
    public List<GrupoDePrivilegio> complete(String query) {
        List<GrupoDePrivilegio> listaFIltrada = new ArrayList<>();
        for (GrupoDePrivilegio b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (GrupoDePrivilegio m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }
    
    public GrupoPrivilegioService getService() {
        return service;
    }

    public void setService(GrupoPrivilegioService service) {
        this.service = service;
    }
}
