package org.example.repository.impl;

import org.example.entity.User;
import org.example.enumeration.Role;
import org.example.repository.inter.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private Connection connection;

    public UserRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<User> login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String hashedPassword = resultSet.getString("password");
                    // Vérifiez si le mot de passe saisi correspond au mot de passe haché
                    if (BCrypt.checkpw(password, hashedPassword)) {
                        int userId = resultSet.getInt("user_id");
                        String userName = resultSet.getString("user_name");
                        Role userRole = Role.valueOf(resultSet.getString("role"));
                        User user = new User(userId, userName, email, hashedPassword, userRole);
                        return Optional.of(user);
                    } else {
                        System.out.println("Mot de passe invalide.");
                    }
                } else {
                    System.out.println("Utilisateur non trouvé.");
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception during login: " + e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public void resetPwd(String email, String password) {
        // Requête SQL pour mettre à jour le mot de passe
        String sql = "UPDATE users SET password = ? WHERE email = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Hacher le mot de passe en utilisant BCrypt
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            // Définir les paramètres de la requête
            statement.setString(1, hashedPassword);
            statement.setString(2, email);

            // Exécuter la mise à jour
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Le mot de passe a été réinitialisé avec succès.");
            } else {
                System.out.println("Aucun utilisateur trouvé avec cet email.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la réinitialisation du mot de passe : " + e.getMessage());
        }
    }
}
