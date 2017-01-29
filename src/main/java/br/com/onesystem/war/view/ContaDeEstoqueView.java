package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.ContaDeEstoqueDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.ContaDeEstoqueBV;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.OperacaoFisica;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class ContaDeEstoqueView implements Serializable {

    private ContaDeEstoqueBV contaDeEstoque;
    private ContaDeEstoque contaDeEstoqueSelecionada;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            ContaDeEstoque novoRegistro = contaDeEstoque.construir();
            if (validaContaDeEstoqueExistente(novoRegistro)) {
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

    public void update() {
        try {
            ContaDeEstoque contaDeEstoqueExistente = contaDeEstoque.construirComID();
            if (contaDeEstoqueExistente.getId() != null) {
                if (validaContaDeEstoqueExistente(contaDeEstoqueExistente)) {
                    new AtualizaDAO<ContaDeEstoque>(ContaDeEstoque.class).atualiza(contaDeEstoqueExistente);
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

    public void delete() {
        try {
            if (contaDeEstoqueSelecionada != null) {
                new RemoveDAO<ContaDeEstoque>(ContaDeEstoque.class).remove(contaDeEstoqueSelecionada, contaDeEstoqueSelecionada.getId());
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

    public void selecionaContaDeEstoque(SelectEvent e) {
        ContaDeEstoque c = (ContaDeEstoque) e.getObject();
        contaDeEstoque = new ContaDeEstoqueBV(c);
        contaDeEstoqueSelecionada = c;
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

    public void limparJanela() {
        contaDeEstoque = new ContaDeEstoqueBV();
        contaDeEstoqueSelecionada = null;
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

}
