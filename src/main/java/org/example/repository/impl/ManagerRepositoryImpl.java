package org.example.repository.impl;

import org.example.entity.Manager;
import org.example.entity.User;
import org.example.repository.inter.ManagerRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ManagerRepositoryImpl implements ManagerRepository {

    private Connection connection;

    public ManagerRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void register(Manager manager) {

        String sql = "INSERT INTO managers (user_name, email, password, role) " +
                "VALUES (?, ?, ?, ?::role_enum) ";

        String hashedPwd = BCrypt.hashpw(manager.getPassword(),BCrypt.gensalt());

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, manager.getUser_name());
            statement.setString(2, manager.getEmail());
            statement.setString(3, hashedPwd);
            statement.setString(4, "MANAGER");

            statement.executeUpdate();
            System.out.println("Manager registered successfully");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
