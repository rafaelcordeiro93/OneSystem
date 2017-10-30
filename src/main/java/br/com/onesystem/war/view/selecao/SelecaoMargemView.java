package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.MargemDAO;
import br.com.onesystem.domain.Margem;
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
public class SelecaoMargemView extends BasicCrudMBImpl<Margem> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private MargemDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("estoque/selecao/selecaoMargem");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/estoque/cadastros/margem";
    }

    @Override
    public List<Margem> complete(String query) {
        buscarDados();
        List<Margem> listaFIltrada = new ArrayList<>();
        for (Margem b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Margem m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    @Override
    public String getIcon() {
        return "fa-gg";
    }
}
