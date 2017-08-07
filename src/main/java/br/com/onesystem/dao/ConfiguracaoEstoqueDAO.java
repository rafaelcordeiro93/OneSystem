package br.com.onesystem.dao;

import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.util.JPAUtil;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class ConfiguracaoEstoqueDAO { 

    public ConfiguracaoEstoque buscar() { 
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            TypedQuery<ConfiguracaoEstoque> query = manager.createNamedQuery("ConfiguracaoEstoque.busca", ConfiguracaoEstoque.class);
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
