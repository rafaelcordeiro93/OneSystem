package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.ComissaoDAO;
import br.com.onesystem.domain.Comissao;
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
public class SelecaoComissaoView extends BasicCrudMBImpl<Comissao> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private ComissaoDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("estoque/selecao/selecaoComissao");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/estoque/cadastros/comissao";
    }

    @Override
    public List<Comissao> complete(String query) {
        buscarDados();
        List<Comissao> listaFIltrada = new ArrayList<>();
        for (Comissao b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Comissao m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

}
