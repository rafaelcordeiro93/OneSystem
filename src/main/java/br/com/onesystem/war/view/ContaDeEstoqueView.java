package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.ContaDeEstoqueDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.ContaDeEstoqueBV;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.OperacaoFisica;
import br.com.onesystem.war.builder.OperacaoDeEstoqueBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ContaDeEstoqueView extends BasicMBImpl<ContaDeEstoque, ContaDeEstoqueBV> implements Serializable {

    private ContaDeEstoqueBV contaDeEstoque;
    private ContaDeEstoque contaDeEstoqueSelecionada;
    private OperacaoDeEstoqueBV operacaoDeEstoque;
    private OperacaoDeEstoqueBV operacaoDeEstoqueSelecionado;
    private List<OperacaoDeEstoqueBV> listaOperacoesDeEstoqueBV;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            ContaDeEstoque novoRegistro = contaDeEstoque.construir();
            if (validaContaDeEstoqueExistente(novoRegistro)) {
                addOperacaoDeEstoque(novoRegistro);
                new AdicionaDAO<ContaDeEstoque>().adiciona(novoRegistro);
                InfoMessage.adicionado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("contaDeEstoque_ja_registrada"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void addOperacaoDeEstoque(ContaDeEstoque novoRegistro) throws DadoInvalidoException {
        for (OperacaoDeEstoqueBV t : listaOperacoesDeEstoqueBV) {
            t.setContaDeEstoque(novoRegistro);
            novoRegistro.getOperacaoDeEstoque().add(t.construir());
        }
    }

    public void update() {
        try {
            if (contaDeEstoqueSelecionada != null) {
                ContaDeEstoque contaDeEstoqueExistente = contaDeEstoque.construirComID();
                if (!validaContaDeEstoqueExistente(contaDeEstoqueExistente)) {

                    atualizaOperacaoDeEstoque(contaDeEstoqueExistente); // Atualiza Operacoes na lista
                    List<OperacaoDeEstoque> deletados = buscaOperacoesDeletadas(contaDeEstoqueExistente); // Busca operacoes deletadas

                    new AtualizaDAO<ContaDeEstoque>().atualiza(contaDeEstoqueExistente);

                    deletaOperacoes(deletados); //deleta operacoes de estoque retiradas da lista e atualiza a lista de operacoes de estoque no banco.

                    InfoMessage.atualizado();
                    limparJanela();
                } else {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("contaDeEstoque_ja_registrada"));
                }
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void atualizaOperacaoDeEstoque(ContaDeEstoque contaDeEstoqueExistente) throws DadoInvalidoException {

        List<OperacaoDeEstoqueBV> listaOperacao = new ArrayList<>();
        for (OperacaoDeEstoqueBV cl : listaOperacoesDeEstoqueBV) {
            OperacaoDeEstoqueBV operacaoDeEstoqueBV = new OperacaoDeEstoqueBV(cl);
            operacaoDeEstoqueBV.setContaDeEstoque(contaDeEstoqueExistente);
            listaOperacao.add(operacaoDeEstoqueBV);
        }

        for (OperacaoDeEstoqueBV o : listaOperacao) {
            try {
                contaDeEstoqueExistente.getOperacaoDeEstoque().set(contaDeEstoqueExistente.getOperacaoDeEstoque().indexOf(o.construirComID()),
                        o.construirComID());
            } catch (ArrayIndexOutOfBoundsException aiob) {
                contaDeEstoqueExistente.getOperacaoDeEstoque().add(o.construir());
            }
        }
    }

    private void deletaOperacoes(List<OperacaoDeEstoque> deletados) throws PersistenceException, DadoInvalidoException {
        // Deleta a operação
        for (OperacaoDeEstoque od : deletados) {
            new RemoveDAO<OperacaoDeEstoque>().remove(od, od.getId());
        }
    }

    private List<OperacaoDeEstoque> buscaOperacoesDeletadas(ContaDeEstoque contaDeEstoqueExistente) throws PersistenceException, DadoInvalidoException {
        List<OperacaoDeEstoque> deletados = new ArrayList<>();

        // Busca Operações deletadas e adiciona na lista de deletados.
        for (OperacaoDeEstoque o : contaDeEstoqueExistente.getOperacaoDeEstoque()) {
            if (!listaOperacoesDeEstoqueBV.isEmpty()) {
                if (o.getId() != null) {
                    boolean encontrou = false;
                    for (OperacaoDeEstoqueBV op : listaOperacoesDeEstoqueBV) {
                        if (o.getId().equals(op.getId())) {
                            encontrou = true;
                            break;
                        }
                    }
                    if (!encontrou) {
                        deletados.add(o);
                    }
                }
            } else {
                deletados.add(o);
            }
        }
        return deletados;
    }

    public void delete() {
        try {
            if (contaDeEstoqueSelecionada != null) {
                new RemoveDAO<ContaDeEstoque>().remove(contaDeEstoqueSelecionada, contaDeEstoqueSelecionada.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    private boolean validaContaDeEstoqueExistente(ContaDeEstoque novoRegistro) {
        List<ContaDeEstoque> lista = new ContaDeEstoqueDAO().buscarContaDeEstoqueW().PorNome(novoRegistro).listaDeResultados();
        return lista.isEmpty();
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof ContaDeEstoque) {
            limparJanela();
            ContaDeEstoque conta = (ContaDeEstoque) obj;
            this.contaDeEstoqueSelecionada = conta;
            this.contaDeEstoque = new ContaDeEstoqueBV(conta);
            selecionaOperacoes();
        } else if (obj instanceof Operacao) {
            this.operacaoDeEstoque.setOperacao((Operacao) obj);
        }
    }

    public void selecionaOperacoes() {
        for (OperacaoDeEstoque op : contaDeEstoque.getOperacaoDeEstoque()) {
            this.listaOperacoesDeEstoqueBV.add(new OperacaoDeEstoqueBV(op));
        }
    }

    public List<OperacaoFisica> getOperacaoFisica() {
        return Arrays.asList(OperacaoFisica.values());
    }

    public void selecionaOperacaoDeEstoque(SelectEvent event) {
        this.operacaoDeEstoqueSelecionado = (OperacaoDeEstoqueBV) event.getObject();
        this.operacaoDeEstoque = new OperacaoDeEstoqueBV(operacaoDeEstoqueSelecionado);
    }

    public void addOperacoesNaLista() {
        try {
            if (operacaoDeEstoque.getOperacao() != null) {
                validaOperacaoDeEstoqueExistente(false);
                operacaoDeEstoque.setId(retornarCodigo());
                if (contaDeEstoqueSelecionada != null) {
                    operacaoDeEstoque.setContaDeEstoque(contaDeEstoqueSelecionada);
                }

                listaOperacoesDeEstoqueBV.add(operacaoDeEstoque);
                limparOperacao();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void updateOperacoesNaLista() {
        try {
            if (operacaoDeEstoqueSelecionado != null) {

                validaOperacaoDeEstoqueExistente(true);
                listaOperacoesDeEstoqueBV.set(listaOperacoesDeEstoqueBV.indexOf(operacaoDeEstoqueSelecionado),
                        operacaoDeEstoque);
                limparOperacao();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void deleteOperacoesNaLista() throws DadoInvalidoException {
        if (operacaoDeEstoqueSelecionado != null) {
            listaOperacoesDeEstoqueBV.remove(operacaoDeEstoqueSelecionado);
            limparOperacao();
        }
    }

    private void validaOperacaoDeEstoqueExistente(boolean update) throws EDadoInvalidoException {

        if (!update) {

            for (OperacaoDeEstoqueBV lista : listaOperacoesDeEstoqueBV) {
                if (operacaoDeEstoque.getOperacao().equals(lista.getOperacao())) {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("operacao_ja_existe"));
                }
            }
        } else {

            boolean valida = false;
            for (OperacaoDeEstoqueBV lista : listaOperacoesDeEstoqueBV) {
                if (operacaoDeEstoque.getOperacao().equals(lista.getOperacao())
                        && !operacaoDeEstoque.getId().equals(lista.getId())) {
                    valida = true;
                    break;
                }
            }
            if (valida) {

                throw new EDadoInvalidoException(new BundleUtil().getMessage("operacao_ja_existe"));
            }

        }
    }

    public void limparOperacao() {
        operacaoDeEstoque = new OperacaoDeEstoqueBV();
        operacaoDeEstoqueSelecionado = null;

    }

    private Long retornarCodigo() {
        Long id = (long) 1;
        if (!listaOperacoesDeEstoqueBV.isEmpty()) {
            for (OperacaoDeEstoqueBV dp : listaOperacoesDeEstoqueBV) {
                if (dp.getId() >= id) {
                    id = dp.getId() + 1;
                }
            }
        }
        return id;
    }

    public void limparJanela() {
        contaDeEstoque = new ContaDeEstoqueBV();
        contaDeEstoqueSelecionada = null;
        operacaoDeEstoque = new OperacaoDeEstoqueBV();
        operacaoDeEstoqueSelecionado = null;
        listaOperacoesDeEstoqueBV = new ArrayList<OperacaoDeEstoqueBV>();

    }

    public void desfazer() {
        if (contaDeEstoqueSelecionada != null) {
            contaDeEstoque = new ContaDeEstoqueBV(contaDeEstoqueSelecionada);
        }
    }

    public ContaDeEstoqueBV getContaDeEstoque() {
        return contaDeEstoque;
    }

    public void setContaDeEstoque(ContaDeEstoqueBV contaDeEstoque) {
        this.contaDeEstoque = contaDeEstoque;
    }

    public ContaDeEstoque getContaDeEstoqueSelecionada() {
        return contaDeEstoqueSelecionada;
    }

    public void setContaDeEstoqueSelecionada(ContaDeEstoque contaDeEstoqueSelecionada) {
        this.contaDeEstoqueSelecionada = contaDeEstoqueSelecionada;
    }

    public OperacaoDeEstoqueBV getOperacoes() {
        return operacaoDeEstoque;
    }

    public void setOperacoes(OperacaoDeEstoqueBV operacaoDeEstoque) {
        this.operacaoDeEstoque = operacaoDeEstoque;
    }

    public OperacaoDeEstoqueBV getOperacoesSelecionado() {
        return operacaoDeEstoqueSelecionado;
    }

    public void setOperacoesSelecionado(OperacaoDeEstoqueBV operacaoDeEstoqueSelecionado) {
        this.operacaoDeEstoqueSelecionado = operacaoDeEstoqueSelecionado;
    }

    public OperacaoDeEstoqueBV getOperacaoDeEstoque() {
        return operacaoDeEstoque;
    }

    public void setOperacaoDeEstoque(OperacaoDeEstoqueBV operacaoDeEstoque) {
        this.operacaoDeEstoque = operacaoDeEstoque;
    }

    public OperacaoDeEstoqueBV getOperacaoDeEstoqueSelecionado() {
        return operacaoDeEstoqueSelecionado;
    }

    public void setOperacaoDeEstoqueSelecionado(OperacaoDeEstoqueBV operacaoDeEstoqueSelecionado) {
        this.operacaoDeEstoqueSelecionado = operacaoDeEstoqueSelecionado;
    }

    public List<OperacaoDeEstoqueBV> getListaOperacoesDeEstoqueBV() {
        return listaOperacoesDeEstoqueBV;
    }

    public void setListaOperacoesDeEstoqueBV(List<OperacaoDeEstoqueBV> listaOperacoesDeEstoqueBV) {
        this.listaOperacoesDeEstoqueBV = listaOperacoesDeEstoqueBV;
    }
}
