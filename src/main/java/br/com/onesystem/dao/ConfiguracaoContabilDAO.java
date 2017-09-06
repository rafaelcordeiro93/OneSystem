package br.com.onesystem.dao;

import br.com.onesystem.domain.ConfiguracaoContabil;
import br.com.onesystem.util.JPAUtil;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public class ConfiguracaoContabilDAO {

    @PersistenceContext
    private EntityManager manager;

    public ConfiguracaoContabil buscar() {
        try {
            TypedQuery<ConfiguracaoContabil> query = manager.createNamedQuery("ConfiguracaoContabil.busca", ConfiguracaoContabil.class);
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
