package com.adi.aivy.service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OCRServiceImplTest {

	private Tesseract tesseractMock;
	private OCRServiceImpl ocrService;

	@BeforeEach
	void setUp() {
		tesseractMock = mock(Tesseract.class);
		ocrService = new OCRServiceImpl(tesseractMock);
	}

	@Test
	void testExtractText_shouldReturnText_whenOCRSuccess() throws Exception {
		File dummyFile = new File("dummy.png");

		when(tesseractMock.doOCR(dummyFile)).thenReturn("Extracted text");

		String result = ocrService.extractText(dummyFile);

		verify(tesseractMock).doOCR(dummyFile);
		assertEquals("Extracted text", result);
	}

	@Test
	void testExtractText_shouldReturnNull_whenTesseractFails() throws Exception {
		File dummyFile = new File("dummy.png");

		when(tesseractMock.doOCR(dummyFile)).thenThrow(new TesseractException("OCR failed"));

		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			ocrService.extractText(dummyFile);
		});

		assertEquals("Error while reading CV. Please try again later.", exception.getMessage());
		verify(tesseractMock).doOCR(dummyFile);
	}
}
