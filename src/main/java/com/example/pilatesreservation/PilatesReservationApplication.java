package com.example.pilatesreservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.pilatesreservation")
public class PilatesReservationApplication {
    public static void main(String[] args) {
        SpringApplication.run(PilatesReservationApplication.class, args);
    }
}
