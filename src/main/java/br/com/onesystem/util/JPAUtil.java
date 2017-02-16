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

    // @PersistenceContext(unitName="homero")
    private static EntityManagerFactory entityManager = Persistence.createEntityManagerFactory("minds");
    private static EntityManager manager;

    //Retorna o entity manager aberto ou cria um novo.
    public static EntityManager getEntityManager() {
        if (manager == null || !manager.isOpen()) {
            manager = entityManager.createEntityManager();
            return manager;
        } else {
            return manager;
        }
    }
}