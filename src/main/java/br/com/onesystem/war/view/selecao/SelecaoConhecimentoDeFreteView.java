package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.ConhecimentoDeFreteDAO;
import br.com.onesystem.domain.ConhecimentoDeFrete;
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
public class SelecaoConhecimentoDeFreteView extends BasicCrudMBImpl<ConhecimentoDeFrete> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private ConhecimentoDeFreteDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("estoque/selecao/selecaoConhecimentoDeFrete");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/estoque/conhecimentoDeFrete";
    }

    @Override
    public List<ConhecimentoDeFrete> complete(String query) {
        buscarDados();
        List<ConhecimentoDeFrete> listaFIltrada = new ArrayList<>();
        if (!StringUtils.containsLetter(query)) {
            for (ConhecimentoDeFrete m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

}
