package br.com.onesystem.war.view;

import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.war.builder.ContaDeEstoqueBV;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.util.Model;
import br.com.onesystem.util.ModelList;
import br.com.onesystem.valueobjects.OperacaoFisica;
import br.com.onesystem.war.builder.OperacaoDeEstoqueBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ContaDeEstoqueView extends BasicMBImpl<ContaDeEstoque, ContaDeEstoqueBV> implements Serializable {
    
    private OperacaoDeEstoqueBV operacaoDeEstoque;
    private Model operacaoDeEstoqueSelecionado;
    private ModelList<OperacaoDeEstoque> operacaoEstoqueList;
    
    @PostConstruct
    public void init() {
        limparJanela();
    }
    
    public void add() {
        try {
            e.setOperacoesDeEstoque(null);
            t = e.construir();
            for (OperacaoDeEstoque op : operacaoEstoqueList.getList()) {
                t.adiciona(new OperacaoDeEstoqueBV(op).construir());
            }
            addNoBanco(t);
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }
    
    public void update() {
        try {
            if (e != null && e.getId() != null) {
                t = e.construirComID();
                atualizaOperacoes();
                
                List<OperacaoDeEstoque> removidos = operacaoEstoqueList.getRemovidos().stream().filter(m -> ((OperacaoDeEstoque) m.getObject()).getId() != null).map(m -> (OperacaoDeEstoque) m.getObject()).collect(Collectors.toList());
                removidos.forEach(c -> t.remove(c));
                
                new AtualizaDAO<>().atualiza(t);
                removeOperacoes(removidos);
                limparJanela();
                InfoMessage.atualizado();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }
    
    private void removeOperacoes(List<OperacaoDeEstoque> removidos) throws DadoInvalidoException, PersistenceException {
        for (OperacaoDeEstoque c : removidos) {
            new RemoveDAO<>().remove(c, c.getId());
        }
    }
    
    private void atualizaOperacoes() throws DadoInvalidoException {
        for (OperacaoDeEstoque op : operacaoEstoqueList.getList()) {
            if (op.getId() == null) {
                t.adiciona(op);
            } else {
                t.atualiza(op);
            }
        }
    }
    
    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof ContaDeEstoque) {
            limparJanela();
            ContaDeEstoque c = (ContaDeEstoque) obj;
            this.e = new ContaDeEstoqueBV(c);
            selecionaConta();
        } else if (obj instanceof Operacao) {
            this.operacaoDeEstoque.setOperacao((Operacao) obj);
        }
    }
    
    public void selecionaConta() {
        if (e == null && (e.getOperacoesDeEstoque() == null || e.getOperacoesDeEstoque().isEmpty())) {
            operacaoEstoqueList = new ModelList<OperacaoDeEstoque>();
        } else {
            operacaoEstoqueList = new ModelList<OperacaoDeEstoque>(e.getOperacoesDeEstoque());
        }
    }
    
    public List<OperacaoFisica> getOperacaoFisica() {
        return Arrays.asList(OperacaoFisica.values());
    }
    
    public void selecionaOperacaoDeEstoque(SelectEvent event) {
        this.operacaoDeEstoqueSelecionado = (Model) event.getObject();
        this.operacaoDeEstoque = new OperacaoDeEstoqueBV((OperacaoDeEstoque) operacaoDeEstoqueSelecionado.getObject());
    }
    
    public void addOperacoesNaLista() {
        try {
            if (operacaoDeEstoque.getOperacao() != null) {
                operacaoEstoqueList.add(operacaoDeEstoque.construir());
                limparOperacao();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }
    
    public void updateOperacoesNaLista() {
        try {
            if (operacaoDeEstoqueSelecionado != null) {
                operacaoDeEstoqueSelecionado.setObject(operacaoDeEstoque.construirComID());
                operacaoEstoqueList.set(operacaoDeEstoqueSelecionado);
                limparOperacao();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }
    
    public void deleteOperacoesNaLista() throws DadoInvalidoException {
        if (operacaoDeEstoqueSelecionado != null) {
            operacaoEstoqueList.remove(operacaoDeEstoqueSelecionado);
            limparOperacao();
        }
    }
    
    public void limparOperacao() {
        operacaoDeEstoque = new OperacaoDeEstoqueBV();
        operacaoDeEstoqueSelecionado = null;
    }
    
    public void limparJanela() {
        e = new ContaDeEstoqueBV();
        operacaoDeEstoque = new OperacaoDeEstoqueBV();
        operacaoEstoqueList = new ModelList<>();
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
    
    public Model getOperacaoDeEstoqueSelecionado() {
        return operacaoDeEstoqueSelecionado;
    }
    
    public void setOperacaoDeEstoqueSelecionado(Model operacaoDeEstoqueSelecionado) {
        this.operacaoDeEstoqueSelecionado = operacaoDeEstoqueSelecionado;
    }
    
    public ModelList<OperacaoDeEstoque> getOperacaoEstoqueList() {
        return operacaoEstoqueList;
    }
    
    public void setOperacaoEstoqueList(ModelList<OperacaoDeEstoque> operacaoEstoqueList) {
        this.operacaoEstoqueList = operacaoEstoqueList;
    }
    
}
