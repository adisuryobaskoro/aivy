package com.adi.aivy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.Model;

import com.adi.aivy.entity.JobSeeker;
import com.adi.aivy.repository.JobSeekerRepository;
import com.adi.aivy.service.AIService;
import com.adi.aivy.service.OCRService;

class JobSeekerControllerTest {

    @Mock
    private OCRService ocrService;

    @Mock
    private AIService aiService;

    @Mock
    private JobSeekerRepository jobSeekerRepository;

    @Mock
    private Model model;

    @InjectMocks
    private JobSeekerController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleUpload_WhenJobSeekerExists() throws Exception {
        // Arrange
        String qualifications = "Java Developer";
        String extractedCv = "Experienced in Spring Boot";

        MockMultipartFile multipartFile = new MockMultipartFile(
                "file", "cv.pdf", "application/pdf", "dummy content".getBytes()
        );

        JobSeeker existing = new JobSeeker();
        existing.setSuggestion("<p>Learn Spring</p>");

        when(ocrService.extractText(any(File.class))).thenReturn(extractedCv);
        when(jobSeekerRepository.findByQualificationsAndExtractedCv(qualifications, extractedCv))
                .thenReturn(existing);

        // Act
        String view = controller.handleUpload(multipartFile, qualifications, model);

        // Assert
        verify(model).addAttribute("suggestionLearningPath", "<p>Learn Spring</p>");
        assertEquals("result", view);
        verify(aiService, never()).restTalk(any(), any());
    }

    @Test
    void testHandleUpload_WhenJobSeekerNotFound() throws Exception {
        // Arrange
        String qualifications = "Backend Engineer";
        String extractedCv = "Knows PostgreSQL and Java";

        MockMultipartFile multipartFile = new MockMultipartFile(
                "file", "resume.pdf", "application/pdf", "dummy content".getBytes()
        );

        when(ocrService.extractText(any(File.class))).thenReturn(extractedCv);
        when(jobSeekerRepository.findByQualificationsAndExtractedCv(qualifications, extractedCv))
                .thenReturn(null);
        when(aiService.restTalk(qualifications, extractedCv)).thenReturn("<ul><li>Learn AI</li></ul>");

        // Act
        String view = controller.handleUpload(multipartFile, qualifications, model);

        // Assert
        verify(aiService).restTalk(qualifications, extractedCv);
        verify(jobSeekerRepository).save(any(JobSeeker.class));
        verify(model).addAttribute(eq("suggestionLearningPath"), contains("Learn AI"));
        assertEquals("result", view);
    }
}
