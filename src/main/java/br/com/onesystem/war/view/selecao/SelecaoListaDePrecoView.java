package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.ListaDePrecoService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoListaDePrecoView extends BasicCrudMBImpl<ListaDePreco> implements Serializable {

    @Inject
    private ListaDePrecoService service;

    @PostConstruct
    public void init() {
        beans = service.buscarListaPrecos();
    }

    public void abrirDialogo() {
        exibirNaTela("estoque/selecao/selecaoListaDePreco");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/estoque/cadastros/listaDePreco";
    }
    
    @Override
    public List<ListaDePreco> complete(String query) {
        List<ListaDePreco> listaFIltrada = new ArrayList<>();
        for (ListaDePreco b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (ListaDePreco m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }
    
    public ListaDePrecoService getService() {
        return service;
    }

    public void setService(ListaDePrecoService service) {
        this.service = service;
    }
}
