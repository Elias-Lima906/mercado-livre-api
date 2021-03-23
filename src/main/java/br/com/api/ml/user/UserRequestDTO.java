package br.com.api.ml.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.api.ml.validation.UniqueValue;


public class UserRequestDTO {

	@NotBlank(message = "O campo email não pode estar em branco ou vazio!")
	@Email(message = "O campo email deve estar num formato aceitável, ex: exemplo@mail.com")
	@UniqueValue(domainName = User.class, fieldName = "email", message = "Já existe um Úsuario cadastrado com o email!")
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

	public User toModel(PasswordEncoder passwordEncoder) {
		return new User(email, passwordEncoder.encode(password));
	}

}
