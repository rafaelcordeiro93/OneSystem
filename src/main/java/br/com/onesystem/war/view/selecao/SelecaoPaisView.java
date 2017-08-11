package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Pais;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.PaisService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoPaisView extends BasicCrudMBImpl<Pais> implements Serializable {

    @Inject
    private PaisService service;

    @PostConstruct
    public void init() {
        beans = service.buscarPais();
    }

    public void abrirDialogo() {
        exibirNaTela("arquivo/selecao/selecaoPais");
    }
    
    @Override
    public String abrirEdicao() {
        return "/menu/arquivo/pais";
    }
    
     @Override
    public List<Pais> complete(String query) {
        List<Pais> listaFIltrada = new ArrayList<>();
        for (Pais b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Pais m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public PaisService getService() {
        return service;
    }

    public void setService(PaisService service) {
        this.service = service;
    }
}
