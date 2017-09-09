/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.Log;
import br.com.onesystem.valueobjects.TipoTransacao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author Rafael-Pc
 */
@Stateless
public class AdicionaDAO<T> {

    @PersistenceContext(unitName = "alkatar")
    private EntityManager em;

    public AdicionaDAO() {
    }

    public void adiciona(T t) throws ConstraintViolationException, DadoInvalidoException {

        try {

            // persiste o objeto e log do mesmo
            em.persist(t);
            em.persist(new Log("Adicionado: " + t, TipoTransacao.INCLUSAO));

        } catch (PersistenceException pe) {
            if (pe.getCause().getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException cve = (ConstraintViolationException) pe.getCause().getCause();
                throw new ConstraintViolationException(getMessage(cve), null, getConstraint(cve));
            }
            throw new FDadoInvalidoException(pe.getCause().toString());
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
            throw new FDadoInvalidoException("<AdicionaDAO> Erro de Gravação: " + ex.getMessage());
        } catch (StackOverflowError soe) {
            System.out.println("Verifique Lista do toString()");
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
