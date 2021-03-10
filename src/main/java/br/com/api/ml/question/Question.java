package br.com.api.ml.question;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.api.ml.product.Product;
import br.com.api.ml.user.User;

@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String title;

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime timestampCreation = LocalDateTime.now();

	@NotNull
	@ManyToOne
	@Valid
	private User user;

	@NotNull
	@ManyToOne
	@Valid
	private Product product;

	@Deprecated
	public Question() {
	}

	public Question(@NotBlank String title, @NotNull @Valid User user, @NotNull @Valid Product product) {
		this.title = title;
		this.user = user;
		this.product = product;
	}

	public String getTitle() {
		return title;
	}

	public LocalDateTime getTimestampCreation() {
		return timestampCreation;
	}

	public Product getProduct() {
		return product;
	}

}
