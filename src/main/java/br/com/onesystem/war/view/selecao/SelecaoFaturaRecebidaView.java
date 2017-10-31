package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.FaturaRecebidaDAO;
import br.com.onesystem.domain.FaturaRecebida;
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
public class SelecaoFaturaRecebidaView extends BasicCrudMBImpl<FaturaRecebida> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private FaturaRecebidaDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoFaturaRecebida");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/faturaRecebida";
    }

    @Override
    public List<FaturaRecebida> complete(String query) {
        buscarDados();
        List<FaturaRecebida> contasFIltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (FaturaRecebida c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    contasFIltradas.add(c);
                }
            }
        }
        return contasFIltradas;
    }

}
