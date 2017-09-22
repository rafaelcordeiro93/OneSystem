package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.OperacaoDAO;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.valueobjects.TipoOperacao;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoOperacaoDevolucaoCondicionalView extends BasicCrudMBImpl<Operacao> implements Serializable {

    @Inject
    private OperacaoDAO operacaoDAO;
    
    @PostConstruct
    public void init() {
        beans = operacaoDAO.porTipoDeOperacao(TipoOperacao.DEVOLUCAO_CONDICIONAL).listaDeResultados();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("contabil/selecaoOperacaoDevolucaoCondicional");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/contabil/operacoes";
    }

    @Override
    public List<Operacao> complete(String query) {
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
