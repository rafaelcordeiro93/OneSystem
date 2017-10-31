package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.DepositoDAO;
import br.com.onesystem.domain.Deposito;
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
public class SelecaoDepositoView extends BasicCrudMBImpl<Deposito> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private DepositoDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("estoque/selecao/selecaoDeposito");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/estoque/cadastros/deposito";
    }

    @Override
    public List<Deposito> complete(String query) {
        buscarDados();
        List<Deposito> listaFIltrada = new ArrayList<>();
        for (Deposito b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Deposito m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }
    
    public String getIcon(){
        return "fa-cubes";
    }

}
