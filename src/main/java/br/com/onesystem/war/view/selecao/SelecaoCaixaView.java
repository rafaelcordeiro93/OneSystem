package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.CaixaDAO;
import br.com.onesystem.domain.Caixa;
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
public class SelecaoCaixaView extends BasicCrudMBImpl<Caixa> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private CaixaDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoCaixa");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/caixa";
    }

    @Override
    public List<Caixa> complete(String query) {
        buscarDados();
        List<Caixa> caixasFIltrados = new ArrayList<>();
        if (!StringUtils.containsLetter(query)) {
            for (Caixa m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    caixasFIltrados.add(m);
                }
            }
        }
        return caixasFIltrados;
    }

}
