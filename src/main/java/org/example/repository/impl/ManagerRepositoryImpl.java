package org.example.repository.impl;

import org.example.entity.Manager;
import org.example.entity.User;
import org.example.repository.inter.ManagerRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            statement.setString(4, "GESTIONNAIRE");

            int result = statement.executeUpdate();
            if(result>0)
                System.out.println("Manager registered successfully");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Optional<Manager> findById(Integer id) {
        String sql = "SELECT * FROM managers WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Directly map the ResultSet to a Manager object
                Manager manager = new Manager();
                manager.setUser_id(resultSet.getInt("user_id"));
                manager.setUser_name(resultSet.getString("user_name"));
                manager.setEmail(resultSet.getString("email"));

                return Optional.of(manager);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


    @Override
    public Optional<Manager> findByEmail(String email) {
        String sql = "SELECT * FROM managers WHERE email = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Directly map the ResultSet to a Manager object
                Manager manager = new Manager();
                manager.setUser_id(resultSet.getInt("user_id"));
                manager.setUser_name(resultSet.getString("user_name"));
                manager.setEmail(resultSet.getString("email"));

                return Optional.of(manager);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Manager> findAll() {
        List<Manager> managers = new ArrayList<>();
        String sql = "SELECT * FROM managers";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                Manager manager = new Manager();
                manager.setUser_id(resultSet.getInt("user_id"));
                manager.setUser_name(resultSet.getString("user_name"));
                manager.setEmail(resultSet.getString("email"));
                // add more colomn
                managers.add(manager);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return managers;
    }

    @Override
    public void update(Manager manager) {

        String sql = "UPDATE managers SET user_name = ?, email = ? WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,manager.getUser_name());
            statement.setString(2,manager.getEmail());
            statement.setInt(3,manager.getUser_id());

            int result = statement.executeUpdate();
            if (result > 0) {
                System.out.println("Manager was successfully Updating");
            } else {
                System.out.println("Updating manager failed, no rows affected");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {

        String sql = "DELETE FROM managers WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);

            int result = statement.executeUpdate();
            if (result > 0) {
                System.out.println("Manager with ID " + id + " was successfully deleted.");
            } else {
                System.out.println("No manager found with ID " + id + ".");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
