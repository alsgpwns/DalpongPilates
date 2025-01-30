package com.example.pilatesreservation.controller;

import com.example.pilatesreservation.model.Reservation;
import com.example.pilatesreservation.service.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // 예약 목록 페이지
    @GetMapping
    public String getAllReservations(Model model) {
        Flux<Reservation> reservations = reservationService.getAllReservations();
        model.addAttribute("reservations", reservations);
        return "reservation-list"; // reservation-list.html 렌더링
    }

    // 예약 추가 페이지 (form)
    @GetMapping("/add")
    public String addReservationForm() {
        return "add-reservation"; // add-reservation.html 렌더링
    }

    // 예약 추가 처리
    @PostMapping
    public Mono<String> addReservation(@ModelAttribute Reservation reservation) {
        return reservationService.addReservation(reservation).thenReturn("redirect:/reservations");
    }

    // 예약 삭제 처리
    @PostMapping("/delete/{id}")
    public Mono<String> deleteReservation(@PathVariable Long id) {
        return reservationService.deleteReservation(id).thenReturn("redirect:/reservations");
    }
}
