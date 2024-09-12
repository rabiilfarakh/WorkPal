package org.example.repository.impl;

import org.example.entity.SpaceType;
import org.example.repository.inter.SpaceTypeRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpaceTypeRepositoryImpl implements SpaceTypeRepository {

    private Connection connection;

    public SpaceTypeRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    @Override
    public SpaceType save(SpaceType space_type) {
        String sql = "INSERT INTO space_type (name, politic) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Set the parameters
            statement.setString(1, space_type.getName());
            statement.setString(2, space_type.getPolitic());

            // Execute the update
            int result = statement.executeUpdate();

            if (result > 0) {
                // Retrieve the generated key (ID)
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        space_type.setSpaceType_id(generatedKeys.getInt(1));
                    }
                }
            } else {
                System.out.println("Insertion failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return space_type;
    }

    // READ - Find by ID
    @Override
    public Optional<SpaceType> findById(Integer spaceType_id) {
        String sql = "SELECT * FROM space_type WHERE space_type_id = ?";
        SpaceType spaceType = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, spaceType_id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    spaceType = new SpaceType();
                    spaceType.setSpaceType_id(resultSet.getInt("space_type_id"));
                    spaceType.setName(resultSet.getString("name"));
                    spaceType.setPolitic(resultSet.getString("politic"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(spaceType);
    }

    // READ - Find all
    @Override
    public List<SpaceType> findAll() {
        String sql = "SELECT * FROM space_type";
        List<SpaceType> spaceTypes = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                SpaceType spaceType = new SpaceType();
                spaceType.setSpaceType_id(resultSet.getInt("space_type_id"));
                spaceType.setName(resultSet.getString("name"));
                spaceType.setPolitic(resultSet.getString("politic"));
                spaceTypes.add(spaceType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return spaceTypes;
    }

    // UPDATE
    @Override
    public SpaceType update(SpaceType space_type) {
        String sql = "UPDATE space_type SET name = ?, politic = ? WHERE space_type_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Set the parameters
            statement.setString(1, space_type.getName());
            statement.setString(2, space_type.getPolitic());
            statement.setInt(3, space_type.getSpaceType_id());

            // Execute the update
            int result = statement.executeUpdate();

            if (result > 0) {
                System.out.println("Update successful.");
            } else {
                System.out.println("Update failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return space_type;
    }

    // DELETE
    @Override
    public boolean deletById(Integer spaceType_id) {
        String sql = "DELETE FROM space_type WHERE space_type_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, spaceType_id);

            // Execute the delete
            int result = statement.executeUpdate();

            return result > 0;  // return true if rows were affected
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
