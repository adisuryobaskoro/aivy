package com.adi.aivy.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.adi.aivy.entity.JobSeeker;
import com.adi.aivy.repository.JobSeekerRepository;
import com.adi.aivy.service.AIService;
import com.adi.aivy.service.OCRService;

@Controller
public class JobSeekerController {

	private OCRService ocrService;
	private AIService aiService;
	private JobSeekerRepository jobseekerRepository;

	public JobSeekerController(OCRService ocrService, AIService aiService, JobSeekerRepository jobseekerRepository) {
		this.ocrService = ocrService;
		this.aiService = aiService;
		this.jobseekerRepository = jobseekerRepository;
	}

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@PostMapping("/v1/ask-aivy")
	public String handleUpload(@RequestParam("file") MultipartFile file,
			@RequestParam("qualifications") String qualifications, Model model) throws IOException {
		File temp = File.createTempFile("cv_", Objects.requireNonNull(file.getOriginalFilename()));
		file.transferTo(temp);

		String extractedText = ocrService.extractText(temp);
		
		JobSeeker jobseeker = jobseekerRepository.findByQualificationsAndExtractedCv(qualifications, extractedText);
		String suggestionLearningPath = "";
		if (jobseeker != null) {
			suggestionLearningPath = jobseeker.getSuggestion();
		} else {
			suggestionLearningPath = aiService.restTalk(qualifications, extractedText);
			jobseeker = new JobSeeker();
			jobseeker.setId(UUID.randomUUID().toString());
			jobseeker.setFileName(temp.getName());
			jobseeker.setUploadedAt(LocalDateTime.now());
			jobseeker.setExtractedCv(extractedText);
			jobseeker.setQualifications(qualifications);
			jobseeker.setSuggestion(suggestionLearningPath.toString());

			jobseekerRepository.save(jobseeker);
		}

		model.addAttribute("suggestionLearningPath", suggestionLearningPath.replace("```html", "").replace("```", ""));
		return "result";
	}
}
