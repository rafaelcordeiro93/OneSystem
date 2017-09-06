/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Usuário
 */
@Stateless
public class JPAUtil {

    @PersistenceContext(unitName = "alkatar")
    private static EntityManager manager;

    @Produces
    public static EntityManager getEntityManager() {
        return manager;
    }

//    private static EntityManagerFactory entityManager = Persistence.createEntityManagerFactory("altakar");
//    private static EntityManager manager;
//
//    public static EntityManager getEntityManager() {
//        if (manager == null || !manager.isOpen()) {
//            manager = entityManager.createEntityManager();
//            return manager;
//        } else {
//            return manager;
//        }
//    }
}
