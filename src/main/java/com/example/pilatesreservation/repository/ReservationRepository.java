package com.example.pilatesreservation.repository;

import com.example.pilatesreservation.model.Reservation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends ReactiveCrudRepository<Reservation, Long> {
}
