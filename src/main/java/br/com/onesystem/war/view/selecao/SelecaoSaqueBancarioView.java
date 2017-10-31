package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.SaqueBancarioDAO;
import br.com.onesystem.domain.SaqueBancario;
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
public class SelecaoSaqueBancarioView extends BasicCrudMBImpl<SaqueBancario> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private SaqueBancarioDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoSaqueBancario");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/consultar/saqueBancario";
    }

    @Override
    public List<SaqueBancario> complete(String query) {
        buscarDados();
        List<SaqueBancario> contasFIltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (SaqueBancario c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    contasFIltradas.add(c);
                }
            }
        }
        return contasFIltradas;
    }

}
