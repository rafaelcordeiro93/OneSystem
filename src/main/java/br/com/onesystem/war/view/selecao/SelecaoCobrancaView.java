package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.CobrancaDAO;
import br.com.onesystem.domain.CobrancaVariavel;
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
public class SelecaoCobrancaView extends BasicCrudMBImpl<CobrancaVariavel> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private CobrancaDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoCobranca");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/consultar/consultaCobranca";
    }

    @Override
    public List<CobrancaVariavel> complete(String query) {
        buscarDados();
        List<CobrancaVariavel> cobrancasFIltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (CobrancaVariavel c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    cobrancasFIltradas.add(c);
                }
            }
        }
        return cobrancasFIltradas;
    }

}
