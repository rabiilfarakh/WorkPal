package org.example.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

    private static Database instance;
    private Connection connection;
    private String url;
    private String username;
    private String password;

    // Constructeur privé pour lire les informations depuis le fichier properties
    private Database() {
        try {
            // Charger les informations de connexion à partir du fichier properties
            Properties properties = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("database.properties");
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new IOException("Fichier de configuration 'database.properties' non trouvé");
            }

            // Récupérer les informations de connexion depuis le fichier
            this.url = properties.getProperty("db.url");
            this.username = properties.getProperty("db.username");
            this.password = properties.getProperty("db.password");

            // Créer la connexion à la base de données
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour obtenir l'instance unique de Database
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    // Méthode pour obtenir la connexion
    public Connection getConnection() {
        return connection;
    }
}
