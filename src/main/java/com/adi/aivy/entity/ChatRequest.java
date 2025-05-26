package com.adi.aivy.entity;

import java.util.List;

public class ChatRequest {
	private String model;
	private List<ChatMessage> messages;


	public ChatRequest() {
	}

	public ChatRequest(String model, List<ChatMessage> messages) {
		super();
		this.model = model;
		this.messages = messages;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<ChatMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<ChatMessage> messages) {
		this.messages = messages;
	}

}
