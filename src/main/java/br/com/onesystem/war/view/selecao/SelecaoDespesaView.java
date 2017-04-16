package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Despesa;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.DespesaService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoDespesaView extends BasicCrudMBImpl<Despesa> implements Serializable {

    @Inject
    private DespesaService service;

    @PostConstruct
    public void init() {
        beans = service.buscarDespesas();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoDespesa");
    }
    
    @Override
    public String abrirEdicao() {
        return "tipoDespesa";
    }
    
    @Override
    public List<Despesa> complete(String query) {
        List<Despesa> listaFIltrada = new ArrayList<>();
        for (Despesa b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Despesa m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public DespesaService getService() {
        return service;
    }

    public void setService(DespesaService service) {
        this.service = service;
    }
}
