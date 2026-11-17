package com.maraichage.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Point unique de connexion JDBC à PostgreSQL.
 * En local : valeurs de repli.
 * En ligne (Render) : variables d'environnement.
 */
public final class Connexion {

    private Connexion() {
    }

    public static Connection get() throws SQLException {
        String url = System.getenv("DB_URL");
        if (url == null || url.isBlank()) {
            String host = System.getenv().getOrDefault("DB_HOST", "localhost");
            String port = System.getenv().getOrDefault("DB_PORT", "5432");
            String base = System.getenv().getOrDefault("DB_NAME", "maraichage");
            url = "jdbc:postgresql://" + host + ":" + port + "/" + base;
        }
        String user = System.getenv().getOrDefault("DB_USER", "postgres");
        String pwd = System.getenv().getOrDefault("DB_PASSWORD", "20262027");
        return DriverManager.getConnection(url, user, pwd);
    }
}
