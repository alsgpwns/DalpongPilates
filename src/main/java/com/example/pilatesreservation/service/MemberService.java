package com.example.pilatesreservation.service;

import com.example.pilatesreservation.model.Member;
import com.example.pilatesreservation.repository.MemberRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 모든 회원 조회
    public Flux<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // 회원 추가
    public Mono<Member> addMember(Member member) {
        return memberRepository.save(member);
    }

    // 회원 삭제
    public Mono<Void> deleteMember(Long id) {
        return memberRepository.deleteById(id);
    }

    // 회원권 잔여 횟수 조회
    public Mono<Integer> getRemainingSessions(String name) {
        return memberRepository.findByName(name)
                .map(Member::getRemainingSessions);
    }

    // 회원권 사용 (예약 시 호출)
    public Mono<Void> useSession(String name) {
        return memberRepository.findByName(name)
                .flatMap(member -> {
                    if (member.getRemainingSessions() > 0) {
                        member.setRemainingSessions(member.getRemainingSessions() - 1);
                        return memberRepository.save(member).then();
                    } else {
                        return Mono.error(new RuntimeException("잔여 회원권이 없습니다."));
                    }
                });
    }
}
