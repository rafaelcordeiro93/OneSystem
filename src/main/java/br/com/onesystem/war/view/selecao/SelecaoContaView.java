package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Conta;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import br.com.onesystem.dao.ContaDAO;
import javax.faces.view.ViewScoped;

@Named
@ViewScoped
public class SelecaoContaView extends BasicCrudMBImpl<Conta> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private ContaDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoConta");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/cadastros/conta";
    }

    @Override
    public List<Conta> complete(String query) {
        buscarDados();
        List<Conta> contasFIltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (Conta c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    contasFIltradas.add(c);
                }
            }
        } else {
            for (Conta c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getNome(), query) || StringUtils.startsWithIgnoreCase(c.getMoeda().getNome(), query)) {
                    contasFIltradas.add(c);
                }
            }
        }
        return contasFIltradas;
    }

}
