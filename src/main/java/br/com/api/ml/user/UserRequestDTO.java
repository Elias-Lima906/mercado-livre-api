package br.com.api.ml.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


public class UserRequestDTO {

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;

	public UserRequestDTO(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public User toModel() {
		return new User(email, password);
	}

}
