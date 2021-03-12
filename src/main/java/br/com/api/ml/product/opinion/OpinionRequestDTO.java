package br.com.api.ml.product.opinion;

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

	@NotBlank
	@Size(max = 500)
	private String description;

	@Deprecated
	public OpinionRequestDTO() {
	}

	public OpinionRequestDTO(@NotNull @Min(1) @Max(5) @Size(max = 1) Integer evaluation,
			@NotBlank @Size(max = 500) String description) {
		this.evaluation = evaluation;
		this.description = description;
	}

	public Integer getEvaluation() {
		return evaluation;
	}

	public String getDescription() {
		return description;
	}

	public Opinion toModel(@NotNull @Valid User user, @NotNull @Valid Product product) {
		Title title = defineTitleByEvaluationValue();
		return new Opinion(evaluation, title, description, user, product);
	}

	private Title defineTitleByEvaluationValue() {

		Title title;

		switch (this.evaluation) {
		case 1:
			title = Title.HORRIVEL;
			return title;
		case 2:
			title = Title.RUIM;
			return title;
		case 3:
			title = Title.MEDIO;
			return title;
		case 4:
			title = Title.BOM;
			return title;
		case 5:
			title = Title.EXCELENTE;
			return title;
		}

		return null;

	}

}
