package com.adi.aivy.entity;

public class ChatMessage {
	private String role; // "user" or "system"
	private String content;

	public ChatMessage() {
	}

	public ChatMessage(String role, String content) {
		super();
		this.role = role;
		this.content = content;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
