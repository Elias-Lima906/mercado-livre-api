package br.com.api.ml.product.question;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

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
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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

	public String getUserEmail() {
		return user.getEmail();
	}

	public static Set<QuestionResponseDTO> toQuestionResponseDTO(Set<Question> questions) {
		return questions.stream().map(question -> new QuestionResponseDTO(question)).collect(Collectors.toSet());
	}

	public void checkIfHasEqualQuestions(Set<Question> questions) {
		if (questions.contains(this)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma pergunta feita pelo úsuario: "
					+ this.getUserEmail() + " a este produto, com o titulo: " + this.getTitle() + ".");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}
