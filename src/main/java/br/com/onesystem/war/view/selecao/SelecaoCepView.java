package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Cep;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.CepService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoCepView extends BasicCrudMBImpl<Cep> implements Serializable {

    @Inject
    private CepService service;

    @PostConstruct
    public void init() {
        beans = service.buscarCeps();
    }

    public void abrirDialogo() {
        exibirNaTela("arquivo/selecao/selecaoCep");
    }
    
    @Override
    public String abrirEdicao() {
        return "/menu/arquivo/cep";
    }
    
     @Override
    public List<Cep> complete(String query) {
        List<Cep> listaFIltrada = new ArrayList<>();
        for (Cep b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getCep(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Cep m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public CepService getService() {
        return service;
    }

    public void setService(CepService service) {
        this.service = service;
    }
}
