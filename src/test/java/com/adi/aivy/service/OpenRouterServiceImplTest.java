package com.adi.aivy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.adi.aivy.entity.ChatMessage;
import com.adi.aivy.entity.ChatResponse;
import com.adi.aivy.entity.ChatResponse.Choice;

class OpenRouterServiceImplTest {

	private String API_KEY = "xxx";
	private String API_URL = "https://api.openai.com/v1/chat/completions";
	private String API_MODEL = "user";

	@Mock
	private OpenRouterServiceImpl service;

	@Mock
	private RestTemplate restTemplate;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		service = new OpenRouterServiceImpl(API_KEY, API_URL, API_MODEL, restTemplate);
	}

	@Test
	void testRestTalk_ShouldReturnAIResponse() {
		ChatMessage responseMessage = new ChatMessage();
		responseMessage.setRole("user");
		responseMessage.setContent("You should learn Spring Cloud.");

		Choice choice = new Choice();
		choice.setMessage(responseMessage);

		ChatResponse chatResponse = new ChatResponse();
		chatResponse.setChoices(List.of(choice));

		ResponseEntity<ChatResponse> responseEntity = new ResponseEntity<>(chatResponse, HttpStatus.OK);

		when(restTemplate.postForEntity(eq(API_URL), any(HttpEntity.class), eq(ChatResponse.class)))
				.thenReturn(responseEntity);

		// Act
		String result = service.restTalk("Java Developer", "Spring Boot experience");

		// Assert
		assertEquals("You should learn Spring Cloud.", result);
	}
}
