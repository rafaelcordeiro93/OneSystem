package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.AjusteDeEstoqueDAO;
import br.com.onesystem.domain.AjusteDeEstoque;
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
public class SelecaoAjusteDeEstoqueView extends BasicCrudMBImpl<AjusteDeEstoque> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private AjusteDeEstoqueDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("/estoque/selecao/selecaoAjusteDeEstoque");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/estoque/ajusteDeEstoque";
    }

    @Override
    public List<AjusteDeEstoque> complete(String query) {
        buscarDados();
        List<AjusteDeEstoque> listaFIltrada = new ArrayList<>();
        if (!StringUtils.containsLetter(query)) {
            for (AjusteDeEstoque a : beans) {
                if (StringUtils.startsWithIgnoreCase(a.getId().toString(), query)) {
                    listaFIltrada.add(a);
                }
            }
        }
        return listaFIltrada;
    }

}
