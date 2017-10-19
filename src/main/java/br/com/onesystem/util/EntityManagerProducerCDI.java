///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package br.com.onesystem.util;
//
//import java.io.Serializable;
//import javax.enterprise.context.ApplicationScoped;
//import javax.enterprise.context.RequestScoped;
//import javax.enterprise.inject.Default;
//import javax.enterprise.inject.Disposes;
//import javax.enterprise.inject.Produces;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import javax.persistence.PersistenceUnit;
//
///**
// *
// * @author Rafael
// */
//@ApplicationScoped
//public class EntityManagerProducerCDI implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @PersistenceUnit(unitName = "alkatar")
//    private EntityManagerFactory appFactory;
//    private EntityManager manager;
//
//    @RequestScoped
//    @Produces
//    @Default
//    public EntityManager createAppEntityManager() {
//        if (manager == null || !manager.isOpen()) {
//            manager = appFactory.createEntityManager();
//        }
//        return manager;
//    }
//
//    public void closeEntityManager(@Disposes EntityManager manager) {
//        if (manager.isOpen()) {
//            manager.close();
//        }
//    }
//
//}
