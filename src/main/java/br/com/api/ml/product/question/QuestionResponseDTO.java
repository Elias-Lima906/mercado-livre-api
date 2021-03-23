package br.com.api.ml.product.question;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class QuestionResponseDTO {

	private String title;
	private String timestampCreation;

	@Deprecated
	public QuestionResponseDTO() {
	}

	public QuestionResponseDTO(Question question) {
		this.title = question.getTitle();
		this.timestampCreation = convertToString(question.getTimestampCreation());
	}

	public String getTitle() {
		return title;
	}

	public String getTimestampCreation() {
		return timestampCreation;
	}

	private String convertToString(LocalDateTime timestampSignUp) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		return timestampSignUp.format(formatter);
	}

}
