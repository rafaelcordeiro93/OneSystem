package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.LancamentoBancario;
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
import br.com.onesystem.dao.LancamentoBancarioDAO;

@Named
@ViewScoped
public class SelecaoLancamentoBancarioView extends BasicCrudMBImpl<LancamentoBancario> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private LancamentoBancarioDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoLancamentoBancario");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/consultar/consultaLancamentoBancario";
    }

    @Override
    public List<LancamentoBancario> complete(String query) {
        buscarDados();
        List<LancamentoBancario> contasFIltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (LancamentoBancario c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    contasFIltradas.add(c);
                }
            }
        }
        return contasFIltradas;
    }

}
