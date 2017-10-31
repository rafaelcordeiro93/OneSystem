package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.TransferenciaDAO;
import br.com.onesystem.domain.Transferencia;
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
public class SelecaoTransferenciaView extends BasicCrudMBImpl<Transferencia> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private TransferenciaDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoTransferencia");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/consultar/transferencia";
    }

    @Override
    public List<Transferencia> complete(String query) {
        buscarDados();
        List<Transferencia> contasFIltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (Transferencia c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    contasFIltradas.add(c);
                }
            }
        }
        return contasFIltradas;
    }

}
