package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.GrupoFiscalDAO;
import br.com.onesystem.domain.GrupoFiscal;
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
public class SelecaoGrupoFiscalView extends BasicCrudMBImpl<GrupoFiscal> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private GrupoFiscalDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("contabil/selecao/selecaoGrupoFiscal");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/contabil/grupoFiscal";
    }

    @Override
    public List<GrupoFiscal> complete(String query) {
        buscarDados();
        List<GrupoFiscal> listaFIltrada = new ArrayList<>();
        for (GrupoFiscal b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (GrupoFiscal m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

}
