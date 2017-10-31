package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.SituacaoFiscalDAO;
import br.com.onesystem.domain.SituacaoFiscal;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Named
@ViewScoped
public class SelecaoSituacaoFiscalView extends BasicCrudMBImpl<SituacaoFiscal> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private SituacaoFiscalDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("contabil/selecao/selecaoSituacaoFiscal");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/contabil/operacoes";
    }

    @Override
    public List<SituacaoFiscal> complete(String query) {
        buscarDados();
        List<SituacaoFiscal> listaFIltrada = new ArrayList<>();
        if (!StringUtils.containsLetter(query)) {
            for (SituacaoFiscal m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public List<SituacaoFiscal> getSituacoesOrdenadasPorSequencia() {
        List<SituacaoFiscal> list = new ArrayList<>(beans);
        list.sort(Comparator.comparing(SituacaoFiscal::getSequencia));
        return list;
    }

}
