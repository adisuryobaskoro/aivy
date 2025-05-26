package com.adi.aivy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.adi.aivy.entity.ChatMessage;
import com.adi.aivy.entity.ChatRequest;
import com.adi.aivy.entity.ChatResponse;
import com.adi.aivy.util.AIUtil;

@Primary
@Service
public class OpenRouterServiceImpl implements AIService {

	private String apiKey;

	private String apiUrl;

	private String model;

	private RestTemplate restTemplate;

	public OpenRouterServiceImpl(@Value("${ai.api-key}") String apiKey, @Value("${ai.url}") String apiUrl,
			@Value("${ai.model}") String model, RestTemplate restTemplate) {
		this.apiKey = apiKey;
		this.apiUrl = apiUrl;
		this.model = model;
		this.restTemplate = restTemplate;
	}

	@Override
	public String directTalk(String qualification, String extractedCv) {
		// not implemented yet
		return null;
	}

	@Override
	public String restTalk(String qualification, String extractedCv) {
		ChatMessage userMessage = new ChatMessage("user", AIUtil.generatePromt(qualification, extractedCv));
		ChatRequest request = new ChatRequest(model, List.of(userMessage));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(apiKey);

		HttpEntity<ChatRequest> httpRequest = new HttpEntity<>(request, headers);
		ResponseEntity<ChatResponse> response = restTemplate.postForEntity(apiUrl, httpRequest, ChatResponse.class);

		return response.getBody().getChoices().get(0).getMessage().getContent();
	}

}
