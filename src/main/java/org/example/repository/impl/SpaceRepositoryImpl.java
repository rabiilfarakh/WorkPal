package org.example.repository.impl;

import org.example.entity.Space;
import org.example.repository.inter.SpaceRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SpaceRepositoryImpl implements SpaceRepository {

    private Connection connection;

    public SpaceRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Space save(Space space) {

        String sql = "INSERT INTO space_type (name,politic) values (?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            int result = statement.executeUpdate();
            if(result>0){

            }else{

            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Space> findById(Integer manager_id) {
        return Optional.empty();
    }

    @Override
    public List<Space> findAll() {
        return List.of();
    }

    @Override
    public Space update(Space space) {
        return null;
    }

    @Override
    public boolean deletById(Integer manager_id) {
        return false;
    }
}
