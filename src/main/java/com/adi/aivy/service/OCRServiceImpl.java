package com.adi.aivy.service;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Service
public class OCRServiceImpl implements OCRService {

	private static final Logger logger = LoggerFactory.getLogger(OCRServiceImpl.class);

	private Tesseract tesseract;

	public OCRServiceImpl(Tesseract tesseract) {
		this.tesseract = tesseract;
	}

	@Override
	public String extractText(File file) {
		return extractText(file, "eng");
	}

	@Override
	public String extractText(File file, String language) {
		try {
			return tesseract.doOCR(file);
		} catch (TesseractException e) {
			logger.error("Failed to extract text from file: {}", file.getName(), e);
			throw new RuntimeException("Error while reading CV. Please try again later.");
		}
	}

}
