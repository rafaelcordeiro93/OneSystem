package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.LoteNotaFiscalDAO;
import br.com.onesystem.domain.LoteNotaFiscal;
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
public class SelecaoLoteNotaFiscalView extends BasicCrudMBImpl<LoteNotaFiscal> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private LoteNotaFiscalDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("contabil/selecao/selecaoLoteNotaFiscal");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/contabil/loteNotaFiscal";
    }

    @Override
    public List<LoteNotaFiscal> complete(String query) {
        buscarDados();
        List<LoteNotaFiscal> moedasFIltradas = new ArrayList<>();
        for (LoteNotaFiscal m : beans) {
            if (StringUtils.startsWithIgnoreCase(m.getNome(), query)) {
                moedasFIltradas.add(m);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (LoteNotaFiscal m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    moedasFIltradas.add(m);
                }
            }
        }
        return moedasFIltradas;
    }

}
