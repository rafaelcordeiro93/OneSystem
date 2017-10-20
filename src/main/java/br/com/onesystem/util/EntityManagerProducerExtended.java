/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import java.io.Serializable;
import javax.ejb.Stateful;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 *
 * @author Rafael
 */
@Stateful
@ApplicationScoped
public class EntityManagerProducerExtended implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "alkatar", type = PersistenceContextType.EXTENDED)
    private EntityManager manager;

    @Produces
    @Default
    public EntityManager getEntityManager() {
        return manager;
    }

}
