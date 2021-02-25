package br.com.api.ml.user;

import java.time.LocalDateTime;
import java.util.Base64;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Email
	@Column(nullable = false)
	private String email;

	@NotBlank
	@Column(nullable = false)
	private String password;

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime timestampSignUp = LocalDateTime.now();

	public User(String email, String password) {
		this.email = email;
		this.password = this.encodePassword(password);
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public LocalDateTime getTimestampSignUp() {
		return timestampSignUp;
	}

	private String encodePassword(String password) {
		return Base64.getEncoder().encodeToString(password.getBytes());
	}

	private String decodePassword(String password) {
		return new String (Base64.getDecoder().decode(password));
	}

}