package br.com.onesystem.war.view;

import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.domain.builder.EstoqueBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.war.builder.AjusteDeEstoqueBV;
import br.com.onesystem.war.service.OperacaoDeEstoqueService;
import br.com.onesystem.war.service.AjusteDeEstoqueService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class AjusteDeEstoqueView extends BasicMBImpl<AjusteDeEstoque, AjusteDeEstoqueBV> implements Serializable {

    @Inject
    private Configuracao configuracao;

    @Inject
    private OperacaoDeEstoqueService operacaoDeEstoqueService;

    @Inject
    private AjusteDeEstoqueService ajusteDeEstoqueService;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            t = e.construir();
            ajusteDeEstoqueService.atualizaEstoque(t);
            addNoBanco(t);
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof AjusteDeEstoque) {
            AjusteDeEstoque ajuste = (AjusteDeEstoque) obj;
            e = new AjusteDeEstoqueBV(ajuste);
        } else if (obj instanceof Operacao) {
            Operacao operacao = (Operacao) obj;
            List<OperacaoDeEstoque> operacoesDeEstoque = operacao.getOperacaoDeEstoque();
            if (operacoesDeEstoque == null || operacoesDeEstoque.isEmpty()) {
                RequestContext rc = RequestContext.getCurrentInstance();
                rc.execute("PF('operacaoAjusteDialog').show()");
            } else {
                e.setOperacao((Operacao) obj);
            }
        } else if (obj instanceof Deposito) {
            e.setDeposito((Deposito) obj);
        } else if (obj instanceof Item) {
            e.setItem((Item) obj);
        }
    }

    public void limparJanela() {
        e = new AjusteDeEstoqueBV();
        t = null;
    }

    public Configuracao getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }
}
