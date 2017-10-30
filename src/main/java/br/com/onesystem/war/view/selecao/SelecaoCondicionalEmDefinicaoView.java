package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.CondicionalDAO;
import br.com.onesystem.domain.Condicional;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.valueobjects.EstadoDeCondicional;
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
public class SelecaoCondicionalEmDefinicaoView extends BasicCrudMBImpl<Condicional> implements Serializable {

    @Inject
    private EntityManager manager;
    
    @Inject
    private CondicionalDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados(){
        beans = dao.porEstado(EstadoDeCondicional.EM_DEFINICAO).listaDeResultados();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("vendas/selecao/selecaoCondicionalEmDefinicao");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/vendas/condicional";
    }

    @Override
    public List<Condicional> complete(String query) {
        buscarDados();
        List<Condicional> comandasFiltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (Condicional c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    comandasFiltradas.add(c);
                }
            }
        }
        return comandasFiltradas;
    }

}
