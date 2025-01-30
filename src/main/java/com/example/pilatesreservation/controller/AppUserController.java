package com.example.pilatesreservation.controller;

import com.example.pilatesreservation.model.AppUser;
import com.example.pilatesreservation.service.AppUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AppUserController { // 클래스명 변경

    private final AppUserService appUserService; // UserService 대신 AppUserService 사용

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/check-user")
    public Mono<AppUser> checkUser(@RequestParam String username) {
        return appUserService.findByUsername(username);
    }
}
