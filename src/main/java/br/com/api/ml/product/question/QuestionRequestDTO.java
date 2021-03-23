package br.com.api.ml.product.question;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.api.ml.product.Product;
import br.com.api.ml.user.User;

public class QuestionRequestDTO {

	@NotBlank
	private String title;

	@Deprecated
	public QuestionRequestDTO() {
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public Question toModel(@NotNull @Valid User user, @NotNull @Valid Product product) {
		return new Question(title, user, product);
	}
}
