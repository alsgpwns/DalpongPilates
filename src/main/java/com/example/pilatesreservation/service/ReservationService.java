package com.example.pilatesreservation.service;

import com.example.pilatesreservation.model.Reservation;
import com.example.pilatesreservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Flux<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Mono<Reservation> addReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Mono<Void> deleteReservation(Long id) {
        return reservationRepository.deleteById(id);
    }
}
