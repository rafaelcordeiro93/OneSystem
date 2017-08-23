package br.com.onesystem.dao;

import br.com.onesystem.domain.ConfiguracaoVenda;
import br.com.onesystem.util.JPAUtil;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class ConfiguracaoVendaDAO {

    public ConfiguracaoVenda buscar() {
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            TypedQuery<ConfiguracaoVenda> query = manager.createNamedQuery("ConfiguracaoVenda.busca", ConfiguracaoVenda.class);
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
