package com.example.pilatesreservation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
	
	  private final ReactiveUserDetailsService userDetailsService;

	    public SecurityConfig(ReactiveUserDetailsService userDetailsService) {
	        this.userDetailsService = userDetailsService;
	    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
            .authorizeExchange(exchange -> exchange
                .pathMatchers("/login", "/static/**", "/register").permitAll() // 비인증 경로 허용
                .anyExchange().authenticated() // 나머지 요청 인증 필요
            )
            .formLogin(form -> form
                .loginPage("/login") // 커스텀 로그인 페이지
                .authenticationSuccessHandler(authenticationSuccessHandler()) // 로그인 성공 핸들러
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // 로그아웃 경로
                .logoutSuccessHandler((exchange, authentication) -> {
                    exchange.getExchange().getResponse().setStatusCode(HttpStatus.FOUND);
                    exchange.getExchange().getResponse().getHeaders().setLocation(URI.create("/login?logout"));
                    return exchange.getExchange().getResponse().setComplete();
                })
            )
            .csrf(ServerHttpSecurity.CsrfSpec::disable) // CSRF 비활성화 (테스트 환경)
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new PasswordEncoder() {
//            @Override
//            public String encode(CharSequence rawPassword) {
//                return rawPassword.toString(); // 인코딩하지 않고 그대로 반환
//            }
//
//            @Override
//            public boolean matches(CharSequence rawPassword, String encodedPassword) {
//                return rawPassword.toString().equals(encodedPassword); // 평문 비교
//            }
//        };
//    }

    // 로그인 성공 핸들러
    private ServerAuthenticationSuccessHandler authenticationSuccessHandler() {
        return (webFilterExchange, authentication) -> {
            ServerWebExchange exchange = webFilterExchange.getExchange();
            exchange.getResponse().setStatusCode(HttpStatus.FOUND);
            exchange.getResponse().getHeaders().setLocation(URI.create("/")); // "/"로 리다이렉트
            return exchange.getResponse().setComplete();
        };
    }
}
