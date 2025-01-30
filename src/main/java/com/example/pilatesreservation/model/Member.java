package com.example.pilatesreservation.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("members")
public class Member {

	@Id
	private Long id;
	private String name;
	private int remainingSessions; // 남은 회원권 횟수

	// 생성자
	public Member() {}

	public Member(String name, int remainingSessions) {
		this.name = name;
		this.remainingSessions = remainingSessions;
	}

	// Getters & Setters
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public int getRemainingSessions() { return remainingSessions; }
	public void setRemainingSessions(int remainingSessions) { this.remainingSessions = remainingSessions; }
}
