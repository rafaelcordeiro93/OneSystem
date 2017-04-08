package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.GrupoPrivilegioService;
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
public class SelecaoGrupoDePrivilegioView extends BasicCrudMBImpl<GrupoDePrivilegio> implements Serializable {

    @ManagedProperty("#{grupoPrivilegioService}")
    private GrupoPrivilegioService service;

    @PostConstruct
    public void init() {
        beans = service.buscarGrupoDePrivilegio();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoGrupoPrivilegio");
    }

    @Override
    public String abrirEdicao() {
        return "grupoPrivilegio";
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
