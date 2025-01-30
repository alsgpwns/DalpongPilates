package com.example.pilatesreservation.repository;

import com.example.pilatesreservation.model.Member;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MemberRepository extends ReactiveCrudRepository<Member, Long> {
    Mono<Member> findByName(String name);
}
