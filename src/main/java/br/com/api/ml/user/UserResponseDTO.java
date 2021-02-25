package br.com.api.ml.user;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserResponseDTO {

	private Long id;
	private String email;
	private String timestampSignUp;

	public UserResponseDTO(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.timestampSignUp = convertToString(user.getTimestampSignUp());
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
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
