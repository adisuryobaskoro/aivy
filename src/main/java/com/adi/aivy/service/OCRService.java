package com.adi.aivy.service;

import java.io.File;

public interface OCRService {
	public String extractText(File file);

	public String extractText(File file, String language);
}
