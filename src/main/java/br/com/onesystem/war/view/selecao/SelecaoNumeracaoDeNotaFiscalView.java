package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.NumeracaoDeNotaFiscalDAO;
import br.com.onesystem.domain.NumeracaoDeNotaFiscal;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Named
@ViewScoped
public class SelecaoNumeracaoDeNotaFiscalView extends BasicCrudMBImpl<NumeracaoDeNotaFiscal> implements Serializable {

    @Inject
    private EntityManager manager;
    
    @Inject
    private NumeracaoDeNotaFiscalDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }
    
    public void buscarDados(){
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("estoque/selecao/selecaoMarca");
    }
    
    @Override
    public String abrirEdicao() {
        return "/menu/estoque/cadastros/marca";
    }
    
     @Override
    public List<NumeracaoDeNotaFiscal> complete(String query) {
        buscarDados();
        List<NumeracaoDeNotaFiscal> listaFIltrada = new ArrayList<>();
        if (!StringUtils.containsLetter(query)) {
            for (NumeracaoDeNotaFiscal m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

}
