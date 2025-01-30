package com.example.pilatesreservation.service;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.pilatesreservation.model.AppUser;
import com.example.pilatesreservation.repository.AppUserRepository;

import reactor.core.publisher.Mono;

@Service
public class CustomUserDetailsService implements ReactiveUserDetailsService {

    private final AppUserRepository userRepository;

    public CustomUserDetailsService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        AppUser user = userRepository.findByUsername(username)
//            .blockOptional() // Mono를 동기적으로 처리
//            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        return User.builder()
//            .username(user.getUsername())
//            .password(user.getPassword())
//            .roles("USER") // 권한 설정
//            .build();
//    }
    
    
    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findById(username) // ID로 사용자 검색
            .map(user -> User.builder()
                .username(user.getId()) // 로그인 ID
                .password(user.getPassword()) // 비밀번호
                .roles(user.getRole()) // 권한
                .build()
            )
            .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")));
    }

    public Mono<String> getUserRealName(String id) {
        return userRepository.findById(id)
            .map(AppUser::getUsername); // 사용자의 실제 이름 반환
    }
}
