package com.adi.aivy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sourceforge.tess4j.Tesseract;

@Configuration
public class OCRConfig {

	@Value("${tesseract.datapath}")
	private String tessDataPath;

	@Bean
	public Tesseract tesseract() {
		Tesseract tesseract = new Tesseract();
		tesseract.setDatapath(tessDataPath);
		tesseract.setLanguage("eng");
		return tesseract;
	}
}
