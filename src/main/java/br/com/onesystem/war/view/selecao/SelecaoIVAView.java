package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.IVA;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.IVAService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoIVAView extends BasicCrudMBImpl<IVA> implements Serializable {

    @Inject
    private IVAService service;

    @PostConstruct
    public void init() {
        beans = service.buscarIVAs();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoIVA");
    }
    
    @Override
    public String abrirEdicao() {
        return "iva";
    }
    
    @Override
    public List<IVA> complete(String query) {
        List<IVA> listaFIltrada = new ArrayList<>();
        for (IVA b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (IVA m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public IVAService getService() {
        return service;
    }

    public void setService(IVAService service) {
        this.service = service;
    }
}
