/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import java.io.IOException;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.hibernate.StaleObjectStateException;

/**
 *
 * @author Rafael
 */
public class EntityManagerFilter implements Filter {

    @Inject
    private EntityManager em;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Initializing filter...");
        System.out.println("Obtaining SessionFactory from static HibernateUtil singleton");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            System.out.println("Starting a database transaction");
            if (em.getTransaction().isActive() == false) {
                em.getTransaction().begin();
            }

            // Call the next filter (continue request processing)  
            chain.doFilter(request, response);

            // Commit and cleanup  
            System.out.println("Committing the database transaction");
            em.getTransaction().commit();

        } catch (StaleObjectStateException staleEx) {
            System.out.println("This interceptor does not implement optimistic concurrency control!");
            System.out.println("Your application will not work until you add compensation actions!");
            // Rollback, close everything, possibly compensate for any permanent changes  
            // during the conversation, and finally restart business conversation. Maybe  
            // give the user of the application a chance to merge some of his work with  
            // fresh data... what you do here depends on your applications design.  
            throw staleEx;
        } catch (Exception ex) {
            if (em.getTransaction().isActive() == false) {
                em.getTransaction().begin();
            }
            em.getTransaction().rollback();

            System.out.println("Erro: " + ex.getMessage());
            throw new ServletException("<AdicionaDAOConsole> Erro de Gravação: " + ex.getMessage());
        } catch (StackOverflowError soe) {
            if (em.getTransaction().isActive() == false) {
                em.getTransaction().begin();
            }
            em.getTransaction().rollback();
            System.out.println("Verifique Lista do toString()");
            throw new ServletException("Verifique Lista do toString()");
        }
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
