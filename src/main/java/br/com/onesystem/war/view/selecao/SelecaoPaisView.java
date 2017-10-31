package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.PaisDAO;
import br.com.onesystem.domain.Pais;
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
public class SelecaoPaisView extends BasicCrudMBImpl<Pais> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private PaisDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("arquivo/selecao/selecaoPais");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/arquivo/pais";
    }

    @Override
    public List<Pais> complete(String query) {
        buscarDados();
        List<Pais> listaFIltrada = new ArrayList<>();
        for (Pais b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Pais m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public String getIcon() {
        return "fa-flag-o";
    }
}
