package com.adi.aivy.util;

import org.springframework.stereotype.Component;

@Component
public class AIUtil {
	public static String generatePromt(String qualification, String extractedCv) {
		StringBuilder sb = new StringBuilder();
		sb.append("Based on the job qualifications below:\n");
		sb.append(qualification);
		sb.append(
				"\n\nWhat learning path should be pursued based on the following CV (this text is the result of OCR extraction) below :\n");
		sb.append(extractedCv);
		sb.append("\n\nPlease only response in html code with bootstrap. Make it eye catching.");
		return sb.toString();
	}
}
