package org.example.service.inter;

import org.example.entity.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationService {

    // Create a new reservation
    Reservation createReservation(Reservation reservation);

    // Retrieve a reservation by its ID
    Optional<Reservation> findById(Integer reservationId);

    // Retrieve all reservations
    List<Reservation> findAll();

    // Update an existing reservation
    Reservation updateReservation(Reservation reservation);

    // Delete a reservation by its ID
    boolean deleteReservation(Integer reservationId);
}
