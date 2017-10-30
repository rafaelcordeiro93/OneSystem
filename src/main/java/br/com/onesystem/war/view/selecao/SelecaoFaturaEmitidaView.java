package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.FaturaEmitidaDAO;
import br.com.onesystem.domain.FaturaEmitida;
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
public class SelecaoFaturaEmitidaView extends BasicCrudMBImpl<FaturaEmitida> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private FaturaEmitidaDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoFaturaEmitida");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/faturaEmitida";
    }

    @Override
    public List<FaturaEmitida> complete(String query) {
        buscarDados();
        List<FaturaEmitida> contasFIltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (FaturaEmitida c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    contasFIltradas.add(c);
                }
            }
        }
        return contasFIltradas;
    }

}
