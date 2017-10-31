package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.ContaDeEstoqueDAO;
import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Named
@ViewScoped
public class SelecaoContaDeEstoqueView extends BasicCrudMBImpl<ContaDeEstoque> implements Serializable {

    @Inject
    private EntityManager manager;
    
    @Inject
    private ContaDeEstoqueDAO dao;
    
    @PostConstruct
    public void init() {
        buscarDados();
    }
    
    public void buscarDados(){
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("estoque/selecao/selecaoContaDeEstoque");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/estoque/contaDeEstoque";
    }
    
    @Override
    public List<ContaDeEstoque> complete(String query) {
        buscarDados();
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
    
}
