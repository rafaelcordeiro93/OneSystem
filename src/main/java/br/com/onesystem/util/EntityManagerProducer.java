/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import java.io.Serializable;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafael
 * TransactionType deve ser Padrão. (Required)
 */
@Stateless
public class EntityManagerProducer implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "alkatar")
    private EntityManager manager;

    @Produces
    @Default
    public EntityManager getEntityManager() {
        return manager;
    }

}
