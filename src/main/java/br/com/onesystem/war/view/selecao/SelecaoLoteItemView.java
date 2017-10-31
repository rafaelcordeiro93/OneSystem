package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.LoteItemDAO;
import br.com.onesystem.domain.LoteItem;
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
public class SelecaoLoteItemView extends BasicCrudMBImpl<LoteItem> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private LoteItemDAO dao;


    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("estoque/selecao/selecaoLoteItem");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/estoque/item";
    }

    @Override
    public List<LoteItem> complete(String query) {
        buscarDados();
        List<LoteItem> moedasFIltradas = new ArrayList<>();
        for (LoteItem m : beans) {
            if (StringUtils.startsWithIgnoreCase(m.getLote().toString(), query)) {
                moedasFIltradas.add(m);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (LoteItem m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    moedasFIltradas.add(m);
                }
            }
        }
        return moedasFIltradas;
    }

}
