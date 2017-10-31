package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.GrupoDAO;
import br.com.onesystem.domain.Grupo;
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
public class SelecaoGrupoView extends BasicCrudMBImpl<Grupo> implements Serializable {

    @Inject
    private EntityManager manager;
    
    @Inject
    private GrupoDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }
    
    public void buscarDados(){
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("estoque/selecao/selecaoGrupo");
    }
    
    @Override
    public String abrirEdicao() {
        return "/menu/estoque/cadastros/grupo";
    }
    
    @Override
    public List<Grupo> complete(String query) {
        buscarDados();
        List<Grupo> listaFIltrada = new ArrayList<>();
        for (Grupo b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Grupo m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    @Override
    public String getIcon(){
        return "fa-object-group";
    }
}
