package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.GrupoFinanceiroDAO;
import br.com.onesystem.domain.GrupoFinanceiro;
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
public class SelecaoGrupoFinanceiroReceitaView extends BasicCrudMBImpl<GrupoFinanceiro> implements Serializable {

    @Inject
    private EntityManager manager;
    
    @Inject
    private GrupoFinanceiroDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.porReceitas().listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("contabil/selecao/selecaoGrupoFinanceiroReceita");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/contabil/grupoFinanceiro";
    }

    @Override
    public List<GrupoFinanceiro> complete(String query) {
        buscarDados();
        List<GrupoFinanceiro> listaFIltrada = new ArrayList<>();
        for (GrupoFinanceiro b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (GrupoFinanceiro m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

}
