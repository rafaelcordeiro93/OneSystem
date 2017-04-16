package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Receita;
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
public class SelecaoReceitaView extends BasicCrudMBImpl<Receita> implements Serializable {

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
    public List<Receita> complete(String query) {
        List<Receita> receitasFiltradas = new ArrayList<>();
        if (!StringUtils.containsLetter(query)) {
            for (Receita t : beans) {
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
