package org.example.service.impl;

import org.example.entity.Reservation;
import org.example.repository.inter.ReservationRepository;
import org.example.service.inter.ReservationService;

import java.util.List;
import java.util.Optional;

public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.createReservation(reservation);
    }

    @Override
    public Optional<Reservation> findById(Integer reservationId) {
        return reservationRepository.findById(reservationId);
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        return reservationRepository.updateReservation(reservation);
    }

    @Override
    public boolean deleteReservation(Integer reservationId) {
        return reservationRepository.deleteReservation(reservationId);
    }
}
