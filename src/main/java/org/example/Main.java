package org.example;

import org.example.config.Database;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        try {
            Database db = Database.getInstance();
            Connection conn = db.getConnection();
            System.out.println("Connexion établie avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
