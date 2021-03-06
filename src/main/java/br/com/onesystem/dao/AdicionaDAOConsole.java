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
public class AdicionaDAOConsole<T> {

    private EntityManager em = JPAUtil.getEntityManager();
  
    public AdicionaDAOConsole() {
    }

    public void adiciona(T t) throws ConstraintViolationException, DadoInvalidoException {

        try {

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
                em.getTransaction().rollback();
                throw new ConstraintViolationException(getMessage(cve), null, getConstraint(cve));
            }
            em.getTransaction().rollback();
            throw new FDadoInvalidoException(pe.getCause().toString());
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
            em.getTransaction().rollback();
            throw new FDadoInvalidoException("<AdicionaDAOConsole> Erro de Gravação: " + ex.getMessage());
        } catch (StackOverflowError soe) {
            System.out.println("Verifique Lista do toString()");
            em.getTransaction().rollback();
            throw new FDadoInvalidoException("Verifique Lista do toString()");
        } finally {
//            em.close(); Comentado na alteração de versão do Hibernate para 5.2
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
