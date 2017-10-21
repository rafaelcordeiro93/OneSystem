package br.com.onesystem.war.service;

import br.com.onesystem.dao.NotaRecebidaDAO;
import br.com.onesystem.domain.ItemDeNota;
import br.com.onesystem.domain.LoteItem;
import br.com.onesystem.domain.NotaRecebida;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;

@Stateless
public class NotaRecebidaService implements Serializable {

    @Inject
    private NotaRecebidaDAO dao;

    @PersistenceContext(unitName = "alkatar")
    private EntityManager em;

    public List<NotaRecebida> buscarNotasRecebidas() {
        return dao.listaDeResultados();
    }

    public void addNotaRecebida(NotaRecebida notaRecebida) throws DadoInvalidoException {

        try {

            for (ItemDeNota it : notaRecebida.getItens()) {
                if (it.getLoteItem().getId() == null) {
                    em.persist(it.getLoteItem());
                } else {
                    em.merge(it.getLoteItem());
                }
            }

            em.persist(notaRecebida);

        } catch (PersistenceException pe) {
            if (pe.getCause().getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException cve = (ConstraintViolationException) pe.getCause().getCause();
                throw new FDadoInvalidoException(getMessage(cve) + " - Constraint: " + getConstraint(cve));
            }
            throw new FDadoInvalidoException(pe.getCause().toString());
        } catch (Exception ex) {
            throw new FDadoInvalidoException("<RecebimentoService> Erro de Gravação: " + ex.getMessage());
        } catch (StackOverflowError soe) {
            throw new FDadoInvalidoException("Verifique Lista do toString()");
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
