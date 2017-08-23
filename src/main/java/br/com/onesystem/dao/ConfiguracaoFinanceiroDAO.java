package br.com.onesystem.dao;

import br.com.onesystem.domain.ConfiguracaoFinanceiro;
import br.com.onesystem.util.JPAUtil;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class ConfiguracaoFinanceiroDAO {

    public ConfiguracaoFinanceiro buscar() {
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            TypedQuery<ConfiguracaoFinanceiro> query = manager.createNamedQuery("ConfiguracaoFinanceiro.busca", ConfiguracaoFinanceiro.class);
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
