package com.example.pilatesreservation.controller;

import com.example.pilatesreservation.model.Member;
import com.example.pilatesreservation.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 모든 회원 조회 페이지
    @GetMapping
    public String getAllMembers(Model model) {
        Flux<Member> members = memberService.getAllMembers();
        model.addAttribute("members", members);
        return "members"; // members.html 렌더링
    }

    // 회원 추가 페이지 (form)
    @GetMapping("/add")
    public String addMemberForm() {
        return "add-member"; // add-member.html 렌더링
    }

    // 회원 추가 처리
    @PostMapping
    public Mono<String> addMember(@ModelAttribute Member member) {
        return memberService.addMember(member).thenReturn("redirect:/members");
    }

    // 회원 삭제 처리
    @PostMapping("/delete/{id}")
    public Mono<String> deleteMember(@PathVariable Long id) {
        return memberService.deleteMember(id).thenReturn("redirect:/members");
    }

    // 회원권 잔여 횟수 확인 페이지
    @GetMapping("/remaining-sessions")
    public String checkRemainingSessions() {
        return "membership-check"; // membership-check.html 렌더링
    }
}
