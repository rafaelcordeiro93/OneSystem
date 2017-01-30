package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.ContaDeEstoqueDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.ContaDeEstoqueBV;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.OperacaoFisica;
import br.com.onesystem.war.builder.OperacaoBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.PersistenceException;
import org.hibernate.Hibernate;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class ContaDeEstoqueView extends BasicMBImpl<ContaDeEstoque> implements Serializable {

    private ContaDeEstoqueBV contaDeEstoque;
    private ContaDeEstoque contaDeEstoqueSelecionada;
    private OperacaoBV operacoes;
    private Operacao operacoesSelecionado;
    private List<Operacao> listaOperacoesDeletada;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            ContaDeEstoque novoRegistro = contaDeEstoque.construir();
            if (validaContaDeEstoqueExistente(novoRegistro)) {
                new AdicionaDAO<ContaDeEstoque>().adiciona(novoRegistro);
                atualizaOperacoes(novoRegistro);
                InfoMessage.adicionado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("contaDeEstoque_ja_registrada"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    /**
     * Metodo utilizado para criar os registro na tabela operacao_contadeestoque
     * da Relacao ManyToMany das tabelas Operacao e ContaDeEstoque
     *
     * @param novoRegistro Registro de contaDeEstoque que foi inserido no banco
     * @throws ConstraintViolationException
     * @throws DadoInvalidoException
     */
    private void atualizaOperacoes(ContaDeEstoque novoRegistro) throws ConstraintViolationException, DadoInvalidoException {

        preparaParaInclusaoDeOperacao(novoRegistro);
        for (Operacao o : novoRegistro.getOperacoes()) {
            o.getContaDeEstoque().add(novoRegistro);
            new AtualizaDAO<Operacao>(Operacao.class).atualiza(o);
        }
    }

    private void preparaParaInclusaoDeOperacao(ContaDeEstoque novoRegistro) {
        for (Operacao t : novoRegistro.getOperacoes()) {
            List<ContaDeEstoque> lista = new ArrayList<ContaDeEstoque>();
            lista.add(novoRegistro);
            System.out.println(lista);
            t.preparaInclusao(lista);
        }
    }

    public void update() {
        try {
            ContaDeEstoque contaDeEstoqueExistente = contaDeEstoque.construirComID();
            if (contaDeEstoqueExistente.getId() != null) {
                if (!validaContaDeEstoqueExistente(contaDeEstoqueExistente)) {
                    atualizaOperacoes(contaDeEstoqueExistente);
                    new AtualizaDAO<ContaDeEstoque>(ContaDeEstoque.class).atualiza(contaDeEstoqueExistente);

                    deletarOperacoes();
                    InfoMessage.atualizado();
                    limparJanela();
                } else {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("contaDeEstoque_ja_registrada"));
                }
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("contaDeEstoque_nao_registrada"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void deletarOperacoes() throws PersistenceException, DadoInvalidoException {
        for (Operacao lista : listaOperacoesDeletada) {
            new RemoveDAO<Operacao>(Operacao.class).remove(lista, lista.getId());
        }
    }

    public void delete() {
        try {
            if (!contaDeEstoque.getOperacao().isEmpty()) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("excluir_operacoes_antes_deletar"));
            }

            if (contaDeEstoqueSelecionada != null) {
                new RemoveDAO<ContaDeEstoque>(ContaDeEstoque.class).remove(contaDeEstoqueSelecionada, contaDeEstoqueSelecionada.getId());
                deletarOperacoes();
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
        if (obj instanceof Operacao) {
            this.operacoesSelecionado = (Operacao) obj;
            this.operacoes = new OperacaoBV(operacoesSelecionado);
            operacoesSelecionado = null;
        } else if (obj instanceof ContaDeEstoque) {
            ContaDeEstoque c = (ContaDeEstoque) obj;
            contaDeEstoque = new ContaDeEstoqueBV(c);
            contaDeEstoqueSelecionada = c;
        }

    }

    public void buscaPorId() {
        Long id = contaDeEstoque.getId();
        if (id != null) {
            try {
                ContaDeEstoqueDAO dao = new ContaDeEstoqueDAO();
                ContaDeEstoque c = dao.buscarContaDeEstoqueW().porId(id).resultado();
                contaDeEstoqueSelecionada = c;
                contaDeEstoque = new ContaDeEstoqueBV(contaDeEstoqueSelecionada);
            } catch (DadoInvalidoException die) {
                limparJanela();
                contaDeEstoque.setId(id);
                die.print();
            }
        }
    }

    public List<OperacaoFisica> getOperacaoFisica() {
        return Arrays.asList(OperacaoFisica.values());
    }

    public void selecionaOperacoes(SelectEvent event) {
        this.operacoesSelecionado = (Operacao) event.getObject();
        this.operacoes = new OperacaoBV(operacoesSelecionado);
    }

    public void addOperacoesNaLista() {
        try {
            operacoes.setId(retornarCodigo());
            for (Operacao lista : contaDeEstoque.getOperacao()) {
                if (operacoes.getNome().equals(lista.getNome())) {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("operacao_ja_existe"));
                }
            }
            contaDeEstoque.getOperacao().add(operacoes.construirComID());
            limparOperacao();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void updateOperacoesNaLista() {
        try {
            if (operacoesSelecionado != null) {
                contaDeEstoque.getOperacao().set(contaDeEstoque.getOperacao().indexOf(operacoesSelecionado), operacoes.construirComID());
                limparOperacao();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void deleteOperacoesNaLista() throws DadoInvalidoException {
        if (operacoesSelecionado != null) {
            listaOperacoesDeletada.add(operacoesSelecionado);
            contaDeEstoque.getOperacao().remove(operacoesSelecionado);
            limparOperacao();
        }
    }

    public void limparOperacao() {
        operacoes = new OperacaoBV();
        operacoesSelecionado = null;

    }

    private Long retornarCodigo() {
        Long id = (long) 1;
        if (!contaDeEstoque.getOperacao().isEmpty()) {
            for (Operacao dp : contaDeEstoque.getOperacao()) {
                if (dp.getId() >= id) {
                    id = dp.getId() + 1;
                }
            }
        }
        return id;
    }

    public void limparJanela() {
        contaDeEstoque = new ContaDeEstoqueBV();
        operacoes = new OperacaoBV();
        contaDeEstoqueSelecionada = null;
        listaOperacoesDeletada = new ArrayList<Operacao>();
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

    public OperacaoBV getOperacoes() {
        return operacoes;
    }

    public void setOperacoes(OperacaoBV operacoes) {
        this.operacoes = operacoes;
    }

    public Operacao getOperacoesSelecionado() {
        return operacoesSelecionado;
    }

    public void setOperacoesSelecionado(Operacao operacoesSelecionado) {
        this.operacoesSelecionado = operacoesSelecionado;
    }

    public List<Operacao> getListaOperacoesDeletada() {
        return listaOperacoesDeletada;
    }

    public void setListaOperacoesDeletada(List<Operacao> listaOperacoesDeletada) {
        this.listaOperacoesDeletada = listaOperacoesDeletada;
    }

    public ContaDeEstoque getBean() {
        return bean;
    }

    public void setBean(ContaDeEstoque bean) {
        this.bean = bean;
    }

}
