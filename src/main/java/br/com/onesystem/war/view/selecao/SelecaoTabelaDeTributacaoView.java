package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.TabelaDeTributacaoDAO;
import br.com.onesystem.domain.TabelaDeTributacao;
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
public class SelecaoTabelaDeTributacaoView extends BasicCrudMBImpl<TabelaDeTributacao> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private TabelaDeTributacaoDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("contabil/selecao/selecaoTabelaDeTributacao");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/contabil/tabelaDeTributacao";
    }

    @Override
    public List<TabelaDeTributacao> complete(String query) {
        buscarDados();
        List<TabelaDeTributacao> listaFIltrada = new ArrayList<>();
        for (TabelaDeTributacao b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (TabelaDeTributacao m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

}
