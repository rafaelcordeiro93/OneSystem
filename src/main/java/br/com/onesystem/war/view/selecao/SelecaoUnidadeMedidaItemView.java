package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.UnidadeMedidaItemDAO;
import br.com.onesystem.domain.UnidadeMedidaItem;
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
public class SelecaoUnidadeMedidaItemView extends BasicCrudMBImpl<UnidadeMedidaItem> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private UnidadeMedidaItemDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("/estoque/selecao/selecaoUnidadeMedidaItem");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/estoque/cadastros/unidadeMedidaItem";
    }

    @Override
    public List<UnidadeMedidaItem> complete(String query) {
        buscarDados();
        List<UnidadeMedidaItem> unidadeFIltrada = new ArrayList<>();
        for (UnidadeMedidaItem u : beans) {
            if (StringUtils.startsWithIgnoreCase(u.getNome(), query)) {
                unidadeFIltrada.add(u);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (UnidadeMedidaItem u : beans) {
                if (StringUtils.startsWithIgnoreCase(u.getId().toString(), query)) {
                    unidadeFIltrada.add(u);
                }
            }
        }
        return unidadeFIltrada;
    }

    @Override
    public String getIcon() {
        return "fa-arrows-v";
    }
}
