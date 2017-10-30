package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.TipoDespesaDAO;
import br.com.onesystem.domain.TipoDespesa;
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
public class SelecaoTipoDespesaView extends BasicCrudMBImpl<TipoDespesa> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private TipoDespesaDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("contabil/selecao/selecaoTipoDespesa");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/contabil/tipoDespesa";
    }

    @Override
    public List<TipoDespesa> complete(String query) {
        buscarDados();
        List<TipoDespesa> listaFIltrada = new ArrayList<>();
        for (TipoDespesa b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (TipoDespesa m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

}
