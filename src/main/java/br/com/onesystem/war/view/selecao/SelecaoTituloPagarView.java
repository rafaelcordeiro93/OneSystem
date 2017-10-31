package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.TituloDAO;
import br.com.onesystem.domain.Titulo;
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
public class SelecaoTituloPagarView extends BasicCrudMBImpl<Titulo> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private TituloDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.aPagar().eAbertas().listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoTituloPagar");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/consultar/consultaCobranca";
    }

    @Override
    public List<Titulo> complete(String query) {
        buscarDados();
        List<Titulo> titulosFiltrados = new ArrayList<>();
        if (!StringUtils.containsLetter(query)) {
            for (Titulo t : beans) {
                if (StringUtils.startsWithIgnoreCase(t.getId().toString(), query)) {
                    titulosFiltrados.add(t);
                }
            }
        }
        return titulosFiltrados;
    }

}
