package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.ChequeDAO;
import br.com.onesystem.domain.Cheque;
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
public class SelecaoChequeView extends BasicCrudMBImpl<Cheque> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private ChequeDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoCheque");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/cadastros/cheque";
    }

    @Override
    public List<Cheque> complete(String query) {
        buscarDados();
        List<Cheque> listaFIltrada = new ArrayList<>();
        if (!StringUtils.containsLetter(query)) {
            for (Cheque m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

}
