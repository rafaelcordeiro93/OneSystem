package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Motivo;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.MotivoService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoMotivoView extends BasicCrudMBImpl<Motivo> implements Serializable {

    @Inject
    private MotivoService service;

    @PostConstruct
    public void init() {
        beans = service.buscarMotivos();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("arquivo/selecao/selecaoMotivo");
    }
    
    @Override
    public String abrirEdicao() {
        return "/menu/arquivo/motivo";
    }
    
    @Override
    public List<Motivo> complete(String query) {
        List<Motivo> motivosFIltrados = new ArrayList<>();
        for (Motivo b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                motivosFIltrados.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Motivo m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    motivosFIltrados.add(m);
                }
            }
        }
        return motivosFIltrados;
    }

    public MotivoService getService() {
        return service;
    }

    public void setService(MotivoService service) {
        this.service = service;
    }
    
    public String getIcon(){
        return "fa-certificate";
    }
}
