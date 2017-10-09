package br.com.onesystem.war.service.impl;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.InfoMessage;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author rauber
 * @param <T> Objeto a ser persistido
 * @param <E> Builder View do objeto
 */
public abstract class BasicMBImpl<T, E> {

    protected T t; //Objeto da persistência
    protected E e; //BuilderView da View

    @Inject
    protected AdicionaDAO<T> adicionaDAO;

    @Inject
    protected AtualizaDAO<T> atualizaDAO;

    @Inject
    protected RemoveDAO<T> removeDAO;

    public abstract void selecionar(SelectEvent event);

    public abstract void limparJanela();

    @PostConstruct
    public void init() {
        limparJanela();
    }

    @Deprecated
    public void buscaPorId() {
    }

    public void add() {
        try {
            BuilderView b = (BuilderView) e;
            addNoBanco((T) b.construir());
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void addNoBanco(T objeto) throws DadoInvalidoException {
        adicionaDAO.adiciona(objeto);
        InfoMessage.adicionado();
        limparJanela();
    }

    public void addNoBanco() throws DadoInvalidoException {
        adicionaDAO.adiciona(t);
        InfoMessage.adicionado();
        limparJanela();
    }

    public void addNoBancoSemLimpar(T objeto) throws DadoInvalidoException {
        adicionaDAO.adiciona(objeto);
        InfoMessage.adicionado();
    }

    public void update() {
        try {
            BuilderView b = (BuilderView) e;
            if (e != null && b.getId() != null) {
                updateNoBanco((T) b.construirComID());
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void updateNoBanco(T objeto) throws DadoInvalidoException {
        atualizaDAO.atualiza(objeto);
        InfoMessage.atualizado();
        limparJanela();
    }

    public void updateNoBanco() throws DadoInvalidoException {
        atualizaDAO.atualiza(t);
        InfoMessage.atualizado();
        limparJanela();
    }

    public void updateNoBancoSemLimpar(T objeto) throws DadoInvalidoException {
        atualizaDAO.atualiza(objeto);
        InfoMessage.atualizado();
    }

    public void delete() {
        try {
            BuilderView b = (BuilderView) e;
            if (e != null && b.getId() != null) {
                deleteNoBanco((T) b.construirComID(), b.getId());
            }
        } catch (DadoInvalidoException di) {
            di.print();
        }
    }

    public void deleteNoBanco(T objeto, Long id) throws DadoInvalidoException {
        removeDAO.remove(objeto, id);
        InfoMessage.removido();
        limparJanela();
    }

    public void deleteNoBancoSemLimpar(T objeto, Long id) throws DadoInvalidoException {
        removeDAO.remove(objeto, id);
        InfoMessage.removido();
    }

    public E getE() {
        if (e == null) {
            limparJanela();
        }
        return e;
    }

    public void setE(E e) {
        this.e = e;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

}
