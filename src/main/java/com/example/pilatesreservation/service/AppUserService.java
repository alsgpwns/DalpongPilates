package com.example.pilatesreservation.service;
import com.example.pilatesreservation.model.AppUser;
import com.example.pilatesreservation.repository.AppUserRepository;

import jakarta.annotation.PostConstruct;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AppUserService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Flux<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    // 사용자 추가 시 비밀번호 암호화
    public Mono<AppUser> addUser(AppUser user) {
        // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Mono<Void> deleteUser(String id) {
        return userRepository.deleteById(id);
    }

    public Mono<AppUser> findByUsername(String username) {
    	System.out.println("findByUsername_username:"+username);
        return userRepository.findByUsername(username);
    }
    
//    // 테스트 유저 추가
//    @PostConstruct
//    public void init() {
//        userRepository.findByUsername("admin")
//            .switchIfEmpty(
//                userRepository.save(new AppUser("test", "testuser", "testuser", "USER", true))
//            )
//            .subscribe(user -> System.out.println("Initialized user: " + user.getUsername()));
//    }
}
