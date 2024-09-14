package org.example.repository.impl;

import org.example.entity.Space;
import org.example.entity.SpaceType;
import org.example.repository.inter.SpaceRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpaceRepositoryImpl implements SpaceRepository {

    private Connection connection;

    public SpaceRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Space save(Space space) {
        String sql = "INSERT INTO spaces (name, location, capacity, price, available, manager_id, space_type_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, space.getName());
            statement.setString(2, space.getLocation());
            statement.setInt(3, space.getCapacity());
            statement.setInt(4, space.getPrice());
            statement.setBoolean(5, space.isAvailable());
            statement.setInt(6, space.getManager().getUser_id());
            statement.setInt(7, space.getSpaceType() != null ? space.getSpaceType().getSpaceType_id() : null);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        space.setSpace_id(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return space;
    }

    @Override
    public Optional<Space> findById(Integer space_id) {
        String sql = "SELECT s.*, st.name AS space_type_name, st.space_type_id AS space_type_id FROM spaces s " +
                "LEFT JOIN space_type st ON s.space_type_id = st.space_type_id " +
                "WHERE s.space_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, space_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Space space = mapRowToSpace(resultSet);
                    return Optional.of(space);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Space> findAll() {
        List<Space> spaces = new ArrayList<>();
        String sql = "SELECT s.*, st.name AS space_type_name, st.space_type_id AS space_type_id FROM spaces s " +
                "LEFT JOIN space_type st ON s.space_type_id = st.space_type_id";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Space space = mapRowToSpace(resultSet);
                spaces.add(space);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return spaces;
    }

    @Override
    public Space update(Space space) {
        String sql = "UPDATE spaces SET name = ?, location = ?, capacity = ?, price = ?, available = ?, " +
                "manager_id = ?, space_type_id = ? WHERE space_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, space.getName());
            statement.setString(2, space.getLocation());
            statement.setInt(3, space.getCapacity());
            statement.setInt(4, space.getPrice());
            statement.setBoolean(5, space.isAvailable());
            statement.setInt(6, space.getManager().getUser_id());
            statement.setInt(7, space.getSpaceType() != null ? space.getSpaceType().getSpaceType_id() : null);
            statement.setInt(8, space.getSpace_id());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return space;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deletById(Integer space_id) {
        String sql = "DELETE FROM spaces WHERE space_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, space_id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Space mapRowToSpace(ResultSet resultSet) throws SQLException {
        Space space = new Space();
        space.setSpace_id(resultSet.getInt("space_id"));
        space.setName(resultSet.getString("name"));
        space.setLocation(resultSet.getString("location"));
        space.setCapacity(resultSet.getInt("capacity"));
        space.setPrice(resultSet.getInt("price"));
        space.setAvailable(resultSet.getBoolean("available"));

        // Mapping SpaceType, handling potential nulls
        int spaceTypeId = resultSet.getInt("space_type_id");
        if (!resultSet.wasNull()) {
            SpaceType spaceType = new SpaceType();
            spaceType.setSpaceType_id(spaceTypeId);
            spaceType.setName(resultSet.getString("space_type_name"));
            space.setSpaceType(spaceType);
        } else {
            space.setSpaceType(null); // Handle null space type
        }

        // Assuming the manager is handled elsewhere
        // space.setManager(getManagerById(resultSet.getInt("manager_id")));  // Implement if needed

        return space;
    }
}
