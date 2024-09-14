package org.example.repository.impl;

import org.example.entity.Reservation;
import org.example.entity.Space;
import org.example.repository.inter.ReservationRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservationRepositoryImpl implements ReservationRepository {

    private final Connection connection;

    public ReservationRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Reservation createReservation(Reservation reservation) {
        String query = "INSERT INTO reservations (start_date, end_date, status, space_id, user_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, new java.sql.Date(reservation.getStart_date().getTime()));
            statement.setDate(2, new java.sql.Date(reservation.getEnd_date().getTime()));
            statement.setBoolean(3, reservation.getStatus());
            statement.setInt(4, reservation.getSpace().getSpace_id());
            statement.setInt(5, reservation.getMember().getUser_id());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating reservation failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reservation.setReservation_id(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating reservation failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservation;
    }

    @Override
    public Optional<Reservation> findById(Integer reservationId) {
        String query = "SELECT * FROM reservations WHERE reservation_id = ?";
        Reservation reservation = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reservationId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    reservation = mapResultSetToReservation(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservation != null ? Optional.of(reservation) : Optional.empty();
    }

    @Override
    public List<Reservation> findAll() {
        String query = "SELECT * FROM reservations";
        List<Reservation> reservations = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                reservations.add(mapResultSetToReservation(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        String query = "UPDATE reservations SET start_date = ?, end_date = ?, status = ?, space_id = ? WHERE reservation_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, new java.sql.Date(reservation.getStart_date().getTime()));
            statement.setDate(2, new java.sql.Date(reservation.getEnd_date().getTime()));
            statement.setBoolean(3, reservation.getStatus());
            statement.setInt(4, reservation.getSpace().getSpace_id());
            statement.setInt(5, reservation.getReservation_id());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservation;
    }

    @Override
    public boolean deleteReservation(Integer reservationId) {
        String query = "DELETE FROM reservations WHERE reservation_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reservationId);
            int affectedRows = statement.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private Reservation mapResultSetToReservation(ResultSet resultSet) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setReservation_id(resultSet.getInt("reservation_id"));
        reservation.setStart_date(resultSet.getDate("start_date"));
        reservation.setEnd_date(resultSet.getDate("end_date"));
        reservation.setStatus(resultSet.getBoolean("status"));

        int spaceId = resultSet.getInt("space_id");
        Space space = new Space();
        space.setSpace_id(spaceId);
        reservation.setSpace(space);

        return reservation;
    }
}
