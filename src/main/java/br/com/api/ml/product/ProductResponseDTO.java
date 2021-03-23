package br.com.api.ml.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import br.com.api.ml.category.Category;
import br.com.api.ml.category.CategoryResponseDTO;
import br.com.api.ml.product.characteristc.Characteristic;
import br.com.api.ml.product.characteristc.CharacteristicResponseDTO;
import br.com.api.ml.product.image.Image;
import br.com.api.ml.product.image.ImageResponseDTO;
import br.com.api.ml.product.opinion.Opinion;
import br.com.api.ml.product.opinion.OpinionResponseDTO;
import br.com.api.ml.product.question.Question;
import br.com.api.ml.product.question.QuestionResponseDTO;

public class ProductResponseDTO {

	private Long id;
	private String name;
	private BigDecimal price;
	private Integer availableQuantity;
	private String description;
	private String timestampSignUp;
	private CategoryResponseDTO category;
	Set<CharacteristicResponseDTO> characteristics;
	Set<ImageResponseDTO> images;
	Set<OpinionResponseDTO> opinions;
	Set<QuestionResponseDTO> questions;

	@Deprecated
	public ProductResponseDTO() {
	}

	public ProductResponseDTO(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.price = product.getPrice();
		this.availableQuantity = product.getAvailableQuantity();
		this.description = product.getDescription();
		this.timestampSignUp = this.convertToString(product.getTimestampSignUp());
		this.category = Category.toCategoryDTO(product.getCategory());
		this.characteristics = Characteristic.toCharacteristicDTO(product.getCharacteristics());
		this.images = Image.toImageResponseDTO(product.getImages());
		this.opinions = Opinion.toOpinionResponseDTO(product.getOpinions());
		this.questions = Question.toQuestionResponseDTO(product.getQuestions());
	}

	public Long getId() {
		return id;
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

	public String getTimestampSignUp() {
		return timestampSignUp;
	}

	public CategoryResponseDTO getCategory() {
		return category;
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

	private String convertToString(LocalDateTime timestampSignUp) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		return timestampSignUp.format(formatter);
	}

}
