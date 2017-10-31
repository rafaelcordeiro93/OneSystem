package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.FaturaLegadaDAO;
import br.com.onesystem.domain.FaturaLegada;
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
public class SelecaoFaturaLegadaView extends BasicCrudMBImpl<FaturaLegada> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private FaturaLegadaDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoFaturaLegada");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/faturaLegada";
    }

    @Override
    public List<FaturaLegada> complete(String query) {
        buscarDados();
        List<FaturaLegada> contasFIltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (FaturaLegada c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    contasFIltradas.add(c);
                }
            }
        }
        return contasFIltradas;
    }

}
