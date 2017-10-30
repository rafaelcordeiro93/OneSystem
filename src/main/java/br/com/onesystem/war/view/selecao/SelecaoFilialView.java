package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.FilialDAO;
import br.com.onesystem.domain.Filial;
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
public class SelecaoFilialView extends BasicCrudMBImpl<Filial> implements Serializable {

    @Inject
    private EntityManager manager;
    
    @Inject
    private FilialDAO dao;
    
    @PostConstruct
    public void init() {
        buscarDados();
    }
    
    public void buscarDados(){
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("topbar/preferencias/selecao/selecaoFilial");
    }
    
    @Override
    public String abrirEdicao() {
        return "/menu/topbar/preferencias/filial";
    }
    
    @Override
    public List<Filial> complete(String query) {
        buscarDados();
        List<Filial> listaFIltrada = new ArrayList<>();
        for (Filial b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getFantasia(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Filial m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

}
