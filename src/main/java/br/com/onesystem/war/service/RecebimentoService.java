package br.com.onesystem.war.service;

import br.com.onesystem.dao.RecebimentoDAO;
import br.com.onesystem.domain.FormaDeCobranca;
import br.com.onesystem.domain.Recebimento;
import br.com.onesystem.domain.TipoDeCobranca;
import br.com.onesystem.domain.Log;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.valueobjects.TipoTransacao;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;

@Stateless
public class RecebimentoService implements Serializable {

    @PersistenceContext(unitName = "alkatar")
    private EntityManager em;

    @Inject
    private RecebimentoDAO dao;

    public List<Recebimento> buscarRecebimentos() {
        return dao.listaDeResultados();
    }

    public Recebimento addRecebimento(Recebimento recebimento) throws DadoInvalidoException {

        try {
            //O cascade não está atualizando a cobrança, realizado atualização manual.
            if (recebimento.getTipoDeCobranca() != null && !recebimento.getTipoDeCobranca().isEmpty()) {
                for (TipoDeCobranca tipo : recebimento.getTipoDeCobranca()) {
                    if (tipo.getCobranca().getId() != null) {
                        em.merge(tipo.getCobranca());
                        em.merge(new Log("Adicionado: " + tipo.getCobranca(), TipoTransacao.ALTERACAO));
                    } else {
                        // persiste o objeto e log do mesmo
                        em.persist(tipo.getCobranca());
                        em.persist(new Log("Adicionado: " + tipo.getCobranca(), TipoTransacao.INCLUSAO));
                    }
                }
            }

//            O cascade não está atualizando a cobrança, realizado atualização manual.
            if (recebimento.getFormasDeCobranca() != null && !recebimento.getFormasDeCobranca().isEmpty()) {
                for (FormaDeCobranca forma : recebimento.getFormasDeCobranca()) {
                    if (forma.getCobranca().getId() != null) {
                        em.merge(forma.getCobranca());
                        em.merge(new Log("Adicionado: " + forma.getCobranca(), TipoTransacao.ALTERACAO));
                    } else {
                        em.persist(forma.getCobranca());
                        em.persist(new Log("Adicionado: " + forma.getCobranca(), TipoTransacao.INCLUSAO));
                    }
                }
            }
            em.persist(recebimento);
            em.flush();

            return recebimento;
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
