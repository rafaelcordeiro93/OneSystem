package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Cotacao;
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
public class SelecaoCotacaoView extends BasicCrudMBImpl<Cotacao> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private CotacaoDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoCotacao");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/cotacao";
    }

    @Override
    public List<Cotacao> complete(String query) {
        buscarDados();
        List<Cotacao> listaFIltrada = new ArrayList<>();
        for (Cotacao b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getConta().getMoeda().getNome(), query) || StringUtils.startsWithIgnoreCase(b.getConta().getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Cotacao m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

}
