package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.DepositoBancarioDAO;
import br.com.onesystem.domain.DepositoBancario;
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
public class SelecaoDepositoBancarioView extends BasicCrudMBImpl<DepositoBancario> implements Serializable {

    @Inject
    private EntityManager manager;
    
    @Inject
    private DepositoBancarioDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }
    
    public void buscarDados(){
        beans = dao.listaDeResultados(manager);
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoDepositoBancario");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/consultar/consultaDepositoBancario";
    }

    @Override
    public List<DepositoBancario> complete(String query) {
        buscarDados();
        List<DepositoBancario> contasFIltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (DepositoBancario c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    contasFIltradas.add(c);
                }
            }
        }
        return contasFIltradas;
    }

}
