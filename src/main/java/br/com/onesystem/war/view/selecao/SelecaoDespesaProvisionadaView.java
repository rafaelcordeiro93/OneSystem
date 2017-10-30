package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.DespesaProvisionadaDAO;
import br.com.onesystem.domain.DespesaProvisionada;
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
public class SelecaoDespesaProvisionadaView extends BasicCrudMBImpl<DespesaProvisionada> implements Serializable {

    @Inject
    private DespesaProvisionadaDAO dao;

    @Inject
    private EntityManager manager;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoDespesaProvisionada");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/cadastros/despesaProvisionada";
    }

    @Override
    public List<DespesaProvisionada> complete(String query) {
        buscarDados();
        List<DespesaProvisionada> listaFIltrada = new ArrayList<>();
        if (!StringUtils.containsLetter(query)) {
            for (DespesaProvisionada m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

}
