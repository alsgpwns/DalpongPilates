package com.example.pilatesreservation.repository;

import com.example.pilatesreservation.model.AppUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface AppUserRepository extends ReactiveCrudRepository<AppUser, String> {
    Mono<AppUser> findByUsername(String username);
}
