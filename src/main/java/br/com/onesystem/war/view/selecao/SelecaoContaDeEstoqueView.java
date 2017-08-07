package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.ContaDeEstoqueService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoContaDeEstoqueView extends BasicCrudMBImpl<ContaDeEstoque> implements Serializable {

    @Inject
    private ContaDeEstoqueService service;

    @PostConstruct
    public void init() {
        beans = service.buscarContaDeEstoque();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoContaDeEstoque");
    }

    @Override
    public String abrirEdicao() {
        return "contaDeEstoque";
    }
    
    @Override
    public List<ContaDeEstoque> complete(String query) {
        List<ContaDeEstoque> listaFIltrada = new ArrayList<>();
        for (ContaDeEstoque b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (ContaDeEstoque m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }
    
    public ContaDeEstoqueService getService() {
        return service;
    }

    public void setService(ContaDeEstoqueService service) {
        this.service = service;
    }
}
