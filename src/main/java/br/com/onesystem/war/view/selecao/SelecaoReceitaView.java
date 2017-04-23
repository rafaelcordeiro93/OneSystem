package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.ReceitaService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoReceitaView extends BasicCrudMBImpl<TipoReceita> implements Serializable {

    @Inject
    private ReceitaService service;

    @PostConstruct
    public void init() {
        beans = service.buscarReceitas();
    }

    @Override
    public String abrirEdicao() {
        return "Receita";
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoReceita");
    }
    
    @Override
    public List<TipoReceita> complete(String query) {
        List<TipoReceita> receitasFiltradas = new ArrayList<>();
        if (!StringUtils.containsLetter(query)) {
            for (TipoReceita t : beans) {
                if (StringUtils.startsWithIgnoreCase(t.getId().toString(), query)) {
                    receitasFiltradas.add(t);
                }
            }
        }
        return receitasFiltradas;
    }

    public ReceitaService getService() {
        return service;
    }

    public void setService(ReceitaService service) {
        this.service = service;
    }
}
