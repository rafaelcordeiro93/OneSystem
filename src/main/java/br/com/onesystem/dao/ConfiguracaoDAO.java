package br.com.onesystem.dao;

import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.JPAUtil;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public class ConfiguracaoDAO {

    @PersistenceContext
    private EntityManager manager;

    public Configuracao buscar() throws EDadoInvalidoException {
        try {
            TypedQuery<Configuracao> query = manager.createNamedQuery("Configuracao.busca", Configuracao.class);
            return query.getSingleResult();
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("Configuracao_nao_definida"));
        }
    }
}
