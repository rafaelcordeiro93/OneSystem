package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.OperacaoDAO;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.valueobjects.TipoOperacao;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoOperacaoDevolucaoCondicionalView extends BasicCrudMBImpl<Operacao> implements Serializable {

    @PostConstruct
    public void init() {
        beans = new OperacaoDAO().buscarOperacao().porTipoDeOperacao(TipoOperacao.DEVOLUCAO_CONDICIONAL).listaDeResultados();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("selecaoOperacaoDevolucaoCondicional");
    }

    @Override
    public String abrirEdicao() {
        return "operacoes";
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
