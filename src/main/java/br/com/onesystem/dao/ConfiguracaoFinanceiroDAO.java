package br.com.onesystem.dao;

import br.com.onesystem.domain.ConfiguracaoFinanceiro;
import br.com.onesystem.util.JPAUtil;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public class ConfiguracaoFinanceiroDAO {

    @PersistenceContext
    private EntityManager manager;

    public ConfiguracaoFinanceiro buscar() {
        try {
            TypedQuery<ConfiguracaoFinanceiro> query = manager.createNamedQuery("ConfiguracaoFinanceiro.busca", ConfiguracaoFinanceiro.class);
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
