package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.PessoaDAO;
import br.com.onesystem.domain.Pessoa;
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
public class SelecaoPessoaFornecedorView extends BasicCrudMBImpl<Pessoa> implements Serializable {

    @Inject
    private EntityManager manager;
    
    @Inject
    private PessoaDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    private void buscarDados() {
        beans = dao.porFornecedor().listaDeResultados(manager);
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("arquivo/selecao/selecaoPessoaFornecedor");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/arquivo/pessoa";
    }

    @Override
    public List<Pessoa> complete(String query) {
        buscarDados();
        List<Pessoa> listaFIltrada = new ArrayList<>();
        for (Pessoa b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Pessoa m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public String getIcon() {
        return "fa-user-o";
    }

}
