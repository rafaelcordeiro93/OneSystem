package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.TipoReceitaDAO;
import br.com.onesystem.domain.TipoReceita;
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
public class SelecaoTipoReceitaView extends BasicCrudMBImpl<TipoReceita> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private TipoReceitaDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    @Override
    public String abrirEdicao() {
        return "/menu/contabil/tipoReceita";
    }

    public void abrirDialogo() {
        exibirNaTela("contabil/selecao/selecaoTipoReceita");
    }

    @Override
    public List<TipoReceita> complete(String query) {
        buscarDados();
        List<TipoReceita> listaFIltrada = new ArrayList<>();
        for (TipoReceita b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (TipoReceita m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

}
