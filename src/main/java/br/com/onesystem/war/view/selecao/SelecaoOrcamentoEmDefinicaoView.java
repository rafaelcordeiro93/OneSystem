package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.OrcamentoDAO;
import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.valueobjects.EstadoDeOrcamento;
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
public class SelecaoOrcamentoEmDefinicaoView extends BasicCrudMBImpl<Orcamento> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private OrcamentoDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.porEstado(EstadoDeOrcamento.EM_DEFINICAO).listaDeResultados(manager);
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("vendas/selecao/selecaoOrcamentoEmDefinicao");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/vendas/orcamento";
    }

    @Override
    public List<Orcamento> complete(String query) {
        buscarDados();
        List<Orcamento> contasFIltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (Orcamento c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    contasFIltradas.add(c);
                }
            }
        }
        return contasFIltradas;
    }

}
