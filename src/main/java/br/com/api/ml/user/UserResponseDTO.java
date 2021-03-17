package br.com.api.ml.user;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserResponseDTO {

	private String message;
	private String timestampSignUp;

	public UserResponseDTO(String message, LocalDateTime timestampSignUp) {
		this.message = message;
		this.timestampSignUp = convertToString(timestampSignUp);
	}

	public String getMessage() {
		return message;
	}

	public String getTimestampSignUp() {
		return timestampSignUp;
	}

	private String convertToString(LocalDateTime timestampSignUp) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

		String formatDateTime = timestampSignUp.format(formatter);

		return formatDateTime;
	}

}
