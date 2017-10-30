package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.ModeloDeRelatorioDAO;
import br.com.onesystem.domain.ModeloDeRelatorio;
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
public class SelecaoModeloDeRelatorioView extends BasicCrudMBImpl<ModeloDeRelatorio> implements Serializable {

    @Inject
    private EntityManager manager;
    
    @Inject
    private ModeloDeRelatorioDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoModeloDeRelatorio");
    }
    
    @Override
    public String abrirEdicao() {
        return "";
    }
    
    @Override
    public List<ModeloDeRelatorio> complete(String query) {
        buscarDados();
        List<ModeloDeRelatorio> listaFIltrada = new ArrayList<>();
        for (ModeloDeRelatorio b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (ModeloDeRelatorio m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

}
