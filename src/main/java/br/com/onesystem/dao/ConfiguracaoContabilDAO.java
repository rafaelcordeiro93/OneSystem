package br.com.onesystem.dao;

import br.com.onesystem.domain.ConfiguracaoContabil;
import br.com.onesystem.util.JPAUtil;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class ConfiguracaoContabilDAO {

    public ConfiguracaoContabil buscar() {
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            TypedQuery<ConfiguracaoContabil> query = manager.createNamedQuery("ConfiguracaoContabil.busca", ConfiguracaoContabil.class);
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
