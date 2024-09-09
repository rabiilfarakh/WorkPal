package org.example.repository.impl;

import org.example.entity.User;
import org.example.repository.inter.MemberRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRepositoryImpl implements MemberRepository {

    private Connection connection;

    public MemberRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void register(User user) {
        String sql = "INSERT INTO users (user_name, email, password, role) " +
                "VALUES (?, ?, ?, ?) " +
                "RETURNING user_id";

        // Hash the password using BCrypt
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUser_name());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashedPassword);
            statement.setString(4, user.getRole().name());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                System.out.println("User registered successfully with ID: " + userId);
            } else {
                System.out.println("User registration failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
