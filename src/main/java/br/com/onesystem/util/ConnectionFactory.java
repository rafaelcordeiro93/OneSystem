/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import com.google.api.services.mapsengine.model.Datasource;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Rauber
 */
public class ConnectionFactory {

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/onesystem", "postgres", "root");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ComboPooledDataSource getDataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("org.postgresql.Driver");
        dataSource.setUser("postgres");
        dataSource.setPassword("root");
        dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/onesystem");
        dataSource.setMinPoolSize(5);
        dataSource.setNumHelperThreads(5);
        // dataSource.setMaxPoolSize(20);
        return dataSource;
    }

}
