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
import br.com.onesystem.util.BundleUtil;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author Rafael-Pc
 */
@Stateless
public class RemoveDAO<T> {

    @PersistenceContext(unitName = "alkatar")
    private EntityManager em;

    public void remove(T t, Long id) throws PersistenceException, DadoInvalidoException {

        try {

            // persiste o objeto e log do mesmo
            em.remove(em.find(t.getClass(), id));
            em.persist(new Log("Excluído: " + t, TipoTransacao.EXCLUSAO));

        } catch (PersistenceException pe) {
            if (pe.getCause().getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException cve = (ConstraintViolationException) pe.getCause().getCause();
                throw new FDadoInvalidoException(getMessage(cve) + " - Constraint: " + getConstraint(cve));
            }
            throw new FDadoInvalidoException(pe.getCause().toString());
        } catch (Exception ex) {
            throw new FDadoInvalidoException("<RemoveDAO> Erro de Remoção: " + ex.getMessage());
        } catch (StackOverflowError soe) {
            throw new FDadoInvalidoException("Verifique Lista do toString()");
        }
    }

    private String getMessage(ConstraintViolationException cve) {
        String msg = String.valueOf(cve.getCause());
        msg = msg.substring(msg.lastIndexOf("on table") + 8, msg.lastIndexOf("Detalhe:"));

        return new BundleUtil().getMessage("registro_referenciado_na_tabela") + msg;
    }

    private String getConstraint(ConstraintViolationException cve) {
        String constraint = String.valueOf(cve.getCause());
        constraint = constraint.substring(constraint.indexOf("(") + 1, constraint.indexOf(")"));
        return constraint;
    }
}
