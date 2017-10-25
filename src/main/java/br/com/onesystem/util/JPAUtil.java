/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Usuário
 */
public class JPAUtil {

    private static EntityManagerFactory entityManager = Persistence.createEntityManagerFactory("alkatar");
    private static EntityManager manager;

    
    public static EntityManager getEntityManager() {
        if (manager == null || !manager.isOpen()) {
            manager = entityManager.createEntityManager();
            return manager;
        } else {
            return manager;
        }
    }
    
}
