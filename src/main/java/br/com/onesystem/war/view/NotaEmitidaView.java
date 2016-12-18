/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.NotaEmitidaBV;
import br.com.onesystem.war.builder.PessoaBV;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Rafael Fernando Rauber
 */
@ManagedBean
@ViewScoped
public class NotaEmitidaView implements Serializable {

    private NotaEmitida notaEmitidaSelecionada;
    private NotaEmitidaBV notaEmitida;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            NotaEmitida novoRegistro = notaEmitida.construir();
            new AdicionaDAO<NotaEmitida>().adiciona(novoRegistro);
            InfoMessage.adicionado();
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            if (notaEmitidaSelecionada != null) {
                NotaEmitida notaEmitidaExistente = notaEmitida.construirComID();
                new AtualizaDAO<NotaEmitida>(NotaEmitida.class).atualiza(notaEmitidaExistente);
                InfoMessage.atualizado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException("!La notaEmitida no se encontra registrada!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (notaEmitidaSelecionada != null) {
                new RemoveDAO<NotaEmitida>(NotaEmitida.class).remove(notaEmitidaSelecionada, notaEmitidaSelecionada.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    public void selecionaOperacao(SelectEvent event) {
        notaEmitida.setOperacao((Operacao) event.getObject());
    }

    public void selecionaPessoa(SelectEvent event) {
        notaEmitida.setPessoa((Pessoa) event.getObject());
    }

    public void selecionaListaDePreco(SelectEvent event) {
        notaEmitida.setListaDePreco((ListaDePreco) event.getObject());
    }

    public void selecionaNotaEmitida(SelectEvent e) {
        NotaEmitida r = (NotaEmitida) e.getObject();
        notaEmitida = new NotaEmitidaBV(r);
        notaEmitidaSelecionada = r;
    }

    public void limparJanela() {
        notaEmitida = new NotaEmitidaBV();
        notaEmitidaSelecionada = null;
    }

    public void desfazer() {
        if (notaEmitidaSelecionada != null) {
            notaEmitida = new NotaEmitidaBV(notaEmitidaSelecionada);
        }
    }

    public NotaEmitida getNotaEmitidaSelecionada() {
        return notaEmitidaSelecionada;
    }

    public void setNotaEmitidaSelecionada(NotaEmitida notaEmitidaSelecionada) {
        this.notaEmitidaSelecionada = notaEmitidaSelecionada;
    }

    public NotaEmitidaBV getNotaEmitida() {
        return notaEmitida;
    }

    public void setNotaEmitida(NotaEmitidaBV notaEmitida) {
        this.notaEmitida = notaEmitida;
    }

}
