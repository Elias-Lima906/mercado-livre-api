package br.com.api.ml.product.page_details;

import java.math.BigDecimal;
import java.util.Set;

import br.com.api.ml.product.Product;
import br.com.api.ml.product.characteristc.Characteristic;
import br.com.api.ml.product.characteristc.CharacteristicResponseDTO;
import br.com.api.ml.product.image.Image;
import br.com.api.ml.product.image.ImageResponseDTO;
import br.com.api.ml.product.opinion.Opinion;
import br.com.api.ml.product.opinion.OpinionResponseDTO;
import br.com.api.ml.product.question.Question;
import br.com.api.ml.product.question.QuestionResponseDTO;

public class ProductPageDetails {

	private String name;
	private BigDecimal price;
	private Integer availableQuantity;
	private String description;
	private Double avarage;
	private Integer numberOfEvaluation;
	Set<CharacteristicResponseDTO> characteristics;
	Set<ImageResponseDTO> images;
	Set<OpinionResponseDTO> opinions;
	Set<QuestionResponseDTO> questions;

	@Deprecated
	public ProductPageDetails() {
	}

	public ProductPageDetails(Product product) {
		this.name = product.getName();
		this.price = product.getPrice();
		this.availableQuantity = product.getAvailableQuantity();
		this.description = product.getDescription();
		this.avarage = product.calculateAvarage();
		this.numberOfEvaluation = product.getNumberOfEvaluation();
		this.characteristics = Characteristic.toCharacteristicDTO(product.getCharacteristics());
		this.images = Image.toImageResponseDTO(product.getImages());
		this.opinions = Opinion.toOpinionResponseDTO(product.getOpinions());
		this.questions = Question.toQuestionResponseDTO(product.getQuestions());
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

	public Set<OpinionResponseDTO> getOpinions() {
		return opinions;
	}

	public Set<QuestionResponseDTO> getQuestions() {
		return questions;
	}

}
