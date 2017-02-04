/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.Log;
import br.com.onesystem.util.JPAUtil;
import br.com.onesystem.valueobjects.TipoTransacao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import javax.faces.FacesException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author Rafael-Pc
 */
public class AdicionaDAO<T> {

    private EntityManager em;

    public AdicionaDAO() {
    }

    public void adiciona(T t) throws ConstraintViolationException, DadoInvalidoException {

        try {

            // consegue a entity manager
            em = JPAUtil.getEntityManager();

            // abre transacao
            em.getTransaction().begin();

            // persiste o objeto e log do mesmo
            em.persist(t);
            em.persist(new Log("Adicionado: " + t, TipoTransacao.INCLUSAO));

            // commita a transacao
            em.getTransaction().commit();

        } catch (PersistenceException pe) {
            if (pe.getCause().getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException cve = (ConstraintViolationException) pe.getCause().getCause();
                throw new ConstraintViolationException(getMessage(cve), null, getConstraint(cve));
            }
        } catch (Exception ex) {
            throw new FDadoInvalidoException("<AdicionaDAO> Erro de Gravação: " + ex.getMessage());
        } finally {
            // fecha a entity manager
            // em.close(); --- Cordeiro&Rauber: Foi tira o fexamento de Entity Manager por nao 
//           carregar a sessao necessaria para a inclusao de lista ManyToMany dentro das classes.  
        }
    }

    private String getMessage(ConstraintViolationException cve) {
        String mensagemFormatada = String.valueOf(cve.getCause())
                .replaceFirst("\\(", "")
                .replaceFirst("\\)", "")
                .replaceAll("\\(", "\\'")
                .replaceAll("\\)", "\\'")
                .replaceAll("\\=", " com valor ")
                .replaceAll("Chave", "Campo");
        mensagemFormatada = mensagemFormatada.substring(mensagemFormatada.indexOf("Detalhe:") + 9, mensagemFormatada.length());
        return mensagemFormatada;
    }

    private String getConstraint(ConstraintViolationException cve) {
        String constraint = String.valueOf(cve.getCause());
        constraint = constraint.substring(constraint.indexOf("(") + 1, constraint.indexOf(")"));
        return constraint;
    }
}
