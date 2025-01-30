package com.example.pilatesreservation.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.pilatesreservation.service.CustomUserDetailsService;
import reactor.core.publisher.Mono;

@Controller
public class IndexController {

    private final CustomUserDetailsService userDetailsService;

    public IndexController(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/")
    public Mono<String> index(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null) {
            return userDetailsService.getUserRealName(userDetails.getUsername())
                .defaultIfEmpty("사용자") // 이름이 없을 경우 기본값 설정
                .doOnNext(realName -> model.addAttribute("username", realName))
                .thenReturn("index"); // index.html 반환
        }
        return Mono.just("index");
    }
}
