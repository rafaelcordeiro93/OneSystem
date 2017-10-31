package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.MoedaDAO;
import br.com.onesystem.domain.Moeda;
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
public class SelecaoMoedaView extends BasicCrudMBImpl<Moeda> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private MoedaDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoMoeda");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/cadastros/moeda";
    }

    @Override
    public List<Moeda> complete(String query) {
        buscarDados();
        List<Moeda> moedasFIltradas = new ArrayList<>();
        for (Moeda m : beans) {
            if (StringUtils.startsWithIgnoreCase(m.getNome(), query)) {
                moedasFIltradas.add(m);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Moeda m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    moedasFIltradas.add(m);
                }
            }
        }
        return moedasFIltradas;
    }

}
