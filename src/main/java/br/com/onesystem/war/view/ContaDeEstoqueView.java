package br.com.onesystem.war.view;

import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.war.builder.ContaDeEstoqueBV;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.valueobjects.OperacaoFisica;
import br.com.onesystem.war.builder.OperacaoDeEstoqueBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ContaDeEstoqueView extends BasicMBImpl<ContaDeEstoque, ContaDeEstoqueBV> implements Serializable {

    private ContaDeEstoque conta;
    private OperacaoDeEstoqueBV operacaoDeEstoque;
    private OperacaoDeEstoque operacaoDeEstoqueSelecionado;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            conta = e.construir();
            addNoBanco(conta);
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof ContaDeEstoque) {
            limparJanela();
            ContaDeEstoque c = (ContaDeEstoque) obj;
            this.e = new ContaDeEstoqueBV(c);
            this.conta = c;
        } else if (obj instanceof Operacao) {
            this.operacaoDeEstoque.setOperacao((Operacao) obj);
        }
    }

    public void selecionaConta() {
        try {
            if (e == null) {
                conta = null;
            } else {
                conta = e.construirComID();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public List<OperacaoFisica> getOperacaoFisica() {
        return Arrays.asList(OperacaoFisica.values());
    }

    public void selecionaOperacaoDeEstoque(SelectEvent event) {
        this.operacaoDeEstoqueSelecionado = (OperacaoDeEstoque) event.getObject();
        this.operacaoDeEstoque = new OperacaoDeEstoqueBV(operacaoDeEstoqueSelecionado);
    }

    public void addOperacoesNaLista() {
        try {
            if (operacaoDeEstoque.getOperacao() != null) {
                conta.adiciona(operacaoDeEstoque.construir());
                new AtualizaDAO<ContaDeEstoque>().atualiza(conta);
                InfoMessage.adicionado();
                limparOperacao();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void updateOperacoesNaLista() {
        try {
            if (operacaoDeEstoqueSelecionado != null) {
                conta.atualiza(operacaoDeEstoqueSelecionado, operacaoDeEstoque.construirComID());
                new AtualizaDAO<ContaDeEstoque>().atualiza(conta);
                InfoMessage.atualizado();
                limparOperacao();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void deleteOperacoesNaLista() throws DadoInvalidoException {
        if (operacaoDeEstoqueSelecionado != null) {
            conta.remove(operacaoDeEstoqueSelecionado);
            new RemoveDAO<OperacaoDeEstoque>().remove(operacaoDeEstoqueSelecionado, operacaoDeEstoqueSelecionado.getId());
            InfoMessage.removido();
            limparOperacao();
        }
    }

    public void limparOperacao() {
        operacaoDeEstoque = new OperacaoDeEstoqueBV();
        operacaoDeEstoqueSelecionado = null;

    }

    public void limparJanela() {
        e = new ContaDeEstoqueBV();
        conta = null;
        operacaoDeEstoque = new OperacaoDeEstoqueBV();
        operacaoDeEstoqueSelecionado = null;
    }

    public OperacaoDeEstoqueBV getOperacoes() {
        return operacaoDeEstoque;
    }

    public void setOperacoes(OperacaoDeEstoqueBV operacaoDeEstoque) {
        this.operacaoDeEstoque = operacaoDeEstoque;
    }

    public OperacaoDeEstoqueBV getOperacaoDeEstoque() {
        return operacaoDeEstoque;
    }

    public void setOperacaoDeEstoque(OperacaoDeEstoqueBV operacaoDeEstoque) {
        this.operacaoDeEstoque = operacaoDeEstoque;
    }

    public OperacaoDeEstoque getOperacaoDeEstoqueSelecionado() {
        return operacaoDeEstoqueSelecionado;
    }

    public void setOperacaoDeEstoqueSelecionado(OperacaoDeEstoque operacaoDeEstoqueSelecionado) {
        this.operacaoDeEstoqueSelecionado = operacaoDeEstoqueSelecionado;
    }

    public ContaDeEstoque getConta() {
        return conta;
    }

    public void setConta(ContaDeEstoque conta) {
        this.conta = conta;
    }
}
