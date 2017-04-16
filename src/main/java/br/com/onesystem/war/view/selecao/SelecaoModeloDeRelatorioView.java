package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.ModeloDeRelatorio;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.ModeloDeRelatorioService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoModeloDeRelatorioView extends BasicCrudMBImpl<ModeloDeRelatorio> implements Serializable {

    @Inject
    private ModeloDeRelatorioService service;

    @PostConstruct
    public void init() {
        beans = service.buscarModeloDeRelatorio();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoModeloDeRelatorio");
    }
    
    @Override
    public String abrirEdicao() {
        return "";
    }
    
    @Override
    public List<ModeloDeRelatorio> complete(String query) {
        List<ModeloDeRelatorio> listaFIltrada = new ArrayList<>();
        for (ModeloDeRelatorio b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (ModeloDeRelatorio m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public ModeloDeRelatorioService getService() {
        return service;
    }

    public void setService(ModeloDeRelatorioService service) {
        this.service = service;
    }
}
