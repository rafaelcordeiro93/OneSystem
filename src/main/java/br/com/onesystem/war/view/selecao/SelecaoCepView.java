package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.CepDAO;
import br.com.onesystem.domain.Cep;
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
public class SelecaoCepView extends BasicCrudMBImpl<Cep> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private CepDAO dao;

    @PostConstruct
    public void init() {
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("arquivo/selecao/selecaoCep");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/arquivo/cep";
    }

    @Override
    public List<Cep> complete(String query) {
        buscarDados();
        List<Cep> listaFIltrada = new ArrayList<>();
        for (Cep b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getCep(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Cep m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public String getIcon() {
        return "fa-cube";
    }
}
