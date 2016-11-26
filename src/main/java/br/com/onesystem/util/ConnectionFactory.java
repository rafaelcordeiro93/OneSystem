/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

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
}
