package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Pagamento;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.dao.PagamentoDAO;
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
public class SelecaoPagamentoView extends BasicCrudMBImpl<Pagamento> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private PagamentoDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoPagamento");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/cadastros/receitaProvisionada";
    }

    @Override
    public List<Pagamento> complete(String query) {
        buscarDados();
        List<Pagamento> listaFIltrada = new ArrayList<>();
        if (!StringUtils.containsLetter(query)) {
            for (Pagamento m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

}
