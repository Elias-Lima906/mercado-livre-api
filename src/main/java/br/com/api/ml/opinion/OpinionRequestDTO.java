package br.com.api.ml.opinion;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.api.ml.product.Product;
import br.com.api.ml.user.User;

public class OpinionRequestDTO {

	@NotNull
	@Min(1)
	@Max(5)
	private Integer evaluation;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Title title;

	@NotBlank
	@Size(max = 500)
	private String description;

	@Deprecated
	public OpinionRequestDTO() {
	}

	public OpinionRequestDTO(@NotNull @Min(1) @Max(5) @Size(max = 1) Integer evaluation, @NotNull Title title,
			@NotBlank @Size(max = 500) String description) {
		this.evaluation = evaluation;
		this.title = title;
		this.description = description;
	}

	public Integer getEvaluation() {
		return evaluation;
	}

	public Title getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Opinion toModel(@NotNull @Valid User user, @NotNull @Valid Product product) {
		return new Opinion(evaluation, title, description, user, product);
	}

}
