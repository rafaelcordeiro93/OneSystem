package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.ListaDePrecoDAO;
import br.com.onesystem.domain.ListaDePreco;
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
public class SelecaoListaDePrecoView extends BasicCrudMBImpl<ListaDePreco> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private ListaDePrecoDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
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
        buscarDados();
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

    public String getIcon() {
        return "fa-list";
    }
}
