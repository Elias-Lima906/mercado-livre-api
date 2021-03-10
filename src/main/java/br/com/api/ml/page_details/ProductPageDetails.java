package br.com.api.ml.page_details;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import br.com.api.ml.characteristc.CharacteristicResponseDTO;
import br.com.api.ml.image.ImageResponseDTO;
import br.com.api.ml.opinion.OpinionResponseDTO;
import br.com.api.ml.product.Product;
import br.com.api.ml.question.QuestionResponseDTO;

public class ProductPageDetails {

	private String name;
	private BigDecimal price;
	private Integer availableQuantity;
	private String description;
	private Double avarage;
	private Integer numberOfEvaluation;
	Set<CharacteristicResponseDTO> characteristics;
	Set<ImageResponseDTO> images;
	List<OpinionResponseDTO> opinions;
	List<QuestionResponseDTO> questions;

	@Deprecated
	public ProductPageDetails() {
	}

	public ProductPageDetails(Product product) {
		this.name = product.getName();
		this.price = product.getPrice();
		this.availableQuantity = product.getAvailableQuantity();
		this.description = product.getDescription();
		this.avarage = product.avarage();
		this.numberOfEvaluation = product.getNumberOfEvaluation();
		this.characteristics = product.toCharacteristicDTO();
		this.images = product.toImageResponseDTO();
		this.opinions = product.toOpinionResponseDTO();
		this.questions = product.toQuestionResponseDTO();
	}

	public Double getAvarage() {
		return avarage;
	}

	public Integer getNumberOfEvaluation() {
		return numberOfEvaluation;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Integer getAvailableQuantity() {
		return availableQuantity;
	}

	public String getDescription() {
		return description;
	}

	public Set<CharacteristicResponseDTO> getCharacteristics() {
		return characteristics;
	}

	public Set<ImageResponseDTO> getImages() {
		return images;
	}

	public List<OpinionResponseDTO> getOpinions() {
		return opinions;
	}

	public List<QuestionResponseDTO> getQuestions() {
		return questions;
	}

}
