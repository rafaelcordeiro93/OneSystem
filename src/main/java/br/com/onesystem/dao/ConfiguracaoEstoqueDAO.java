package br.com.onesystem.dao;

import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.util.JPAUtil;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public class ConfiguracaoEstoqueDAO {

    @PersistenceContext
    private EntityManager manager;

    public ConfiguracaoEstoque buscar() {
        try {
            TypedQuery<ConfiguracaoEstoque> query = manager.createNamedQuery("ConfiguracaoEstoque.busca", ConfiguracaoEstoque.class);
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
