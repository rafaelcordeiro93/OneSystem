package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.OperacaoDAO;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.valueobjects.TipoLancamento;
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
public class SelecaoOperacaoOutrosView extends BasicCrudMBImpl<Operacao> implements Serializable {

    @Inject
    private EntityManager manager;
    
    @Inject
    private OperacaoDAO operacaoDAO;

    @PostConstruct
    public void init() {
        buscarDados();
    }
    
    public void buscarDados(){
        beans = operacaoDAO.porTipoDeLancamento(TipoLancamento.OUTROS).listaDeResultados(manager);
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("contabil/selecao/selecaoOperacaoOutros");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/contabil/operacoes";
    }

    @Override
    public List<Operacao> complete(String query) {
        buscarDados();
        List<Operacao> listaFIltrada = new ArrayList<>();
        for (Operacao b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Operacao m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

}
