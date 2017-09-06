package br.com.onesystem.dao;

import br.com.onesystem.domain.ConfiguracaoVenda;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public class ConfiguracaoVendaDAO {

    @PersistenceContext
    private EntityManager manager;

    public ConfiguracaoVenda buscar() {
        try {
            TypedQuery<ConfiguracaoVenda> query = manager.createNamedQuery("ConfiguracaoVenda.busca", ConfiguracaoVenda.class);
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
