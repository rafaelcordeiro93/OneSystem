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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author Rafael-Pc
 */
public class AtualizaDAO<T> {

    private EntityManager em = JPAUtil.getEntityManager();

    ;
    
    public void atualiza(T t) throws ConstraintViolationException, DadoInvalidoException {

        try {

            // abre transacao
            em.getTransaction().begin();

            // persiste o objeto e log do mesmo
            em.merge(t);
            em.persist(new Log("Alterado: " + t, TipoTransacao.ALTERACAO));

            // commita a transacao
            em.getTransaction().commit();

        } catch (PersistenceException pe) {
            System.out.println("PersistenceException:  " + pe);
            try {

                if (pe.getCause().getCause() instanceof ConstraintViolationException) {
                    ConstraintViolationException cve = (ConstraintViolationException) pe.getCause().getCause();
                    throw new ConstraintViolationException(getMessage(cve), null, getConstraint(cve));
                }
            } catch (NullPointerException e) {
                throw new FDadoInvalidoException("null pointer" + e.getCause());
            }
        } catch (StackOverflowError soe) {
            System.out.println("Verifique Lista do toString()");
            throw new FDadoInvalidoException("Verifique Lista do toString()");
        } catch (Exception ex) {
            throw new FDadoInvalidoException("<AtualizaDAO> Erro de Gravação: " + ex.getMessage());
        } finally {
            // fecha a entity manager
            em.close();
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
