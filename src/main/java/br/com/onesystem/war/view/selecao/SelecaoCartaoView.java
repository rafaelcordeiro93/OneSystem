package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.CartaoDAO;
import br.com.onesystem.domain.Cartao;
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
public class SelecaoCartaoView extends BasicCrudMBImpl<Cartao> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private CartaoDAO dao;

    @PostConstruct
    public void init() {
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoCartao");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/cadastros/cartao";
    }

    @Override
    public List<Cartao> complete(String query) {
        buscarDados();
        List<Cartao> listaFIltrada = new ArrayList<>();
        for (Cartao b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Cartao m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

}
