package br.com.api.ml.product.opinion;

public class OpinionResponseDTO {

	private Integer evaluation;
	private Title title;
	private String description;

	@Deprecated
	public OpinionResponseDTO() {
	}

	public OpinionResponseDTO(Opinion opinion) {
		this.evaluation = opinion.getEvaluation();
		this.title = opinion.getTitle();
		this.description = opinion.getDescription();
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

}
