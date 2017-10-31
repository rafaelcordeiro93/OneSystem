package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.BoletoDeCartaoDAO;
import br.com.onesystem.domain.BoletoDeCartao;
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
public class SelecaoBoletoDeCartaoView extends BasicCrudMBImpl<BoletoDeCartao> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private BoletoDeCartaoDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoBoletoDeCartao");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/cadastros/boletoDeCartao";
    }

    @Override
    public List<BoletoDeCartao> complete(String query) {
        buscarDados();
        List<BoletoDeCartao> boletosFIltrados = new ArrayList<>();
        if (!StringUtils.containsLetter(query)) {
            for (BoletoDeCartao m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    boletosFIltrados.add(m);
                }
            }
        }
        return boletosFIltrados;
    }

}
