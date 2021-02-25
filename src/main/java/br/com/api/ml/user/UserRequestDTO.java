package br.com.api.ml.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public class UserRequestDTO {

	@NotBlank(message = "O campo email não pode estar em branco!")
	@Email(message = "O campo email deve estar num formato aceitável, ex: exemplo@mail.com")
	private String email;

	@NotBlank(message = "O campo password não pode estar em branco!")
	@Size(min = 6)
	private String password;

	public UserRequestDTO(@NotBlank @Email String email, @NotBlank String password) {
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
