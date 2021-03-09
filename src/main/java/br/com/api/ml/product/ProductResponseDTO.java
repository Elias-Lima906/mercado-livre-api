package br.com.api.ml.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import br.com.api.ml.category.CategoryResponseDTO;
import br.com.api.ml.characteristc.CharacteristicResponseDTO;
import br.com.api.ml.image.ImageResponseDTO;
import br.com.api.ml.opinion.OpinionResponseDTO;

public class ProductResponseDTO {

	private String name;
	private BigDecimal price;
	private Integer availableQuantity;
	private String description;
	private String timestampSignUp;
	private CategoryResponseDTO category;
	Set<CharacteristicResponseDTO> characteristics;
	Set<ImageResponseDTO> images;
	List<OpinionResponseDTO> opinions;

	@Deprecated
	public ProductResponseDTO() {
	}

	public ProductResponseDTO(Product product) {
		this.name = product.getName();
		this.price = product.getPrice();
		this.availableQuantity = product.getAvailableQuantity();
		this.description = product.getDescription();
		this.timestampSignUp = this.convertToString(product.getTimestampSignUp());
		this.category = product.toCategoryDTO();
		this.characteristics = product.toCharacteristicDTO();
		this.images = product.toImageResponseDTO();
		this.opinions = product.toOpinionResponseDTO();
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

	public List<OpinionResponseDTO> getOpinions() {
		return opinions;
	}

	private String convertToString(LocalDateTime timestampSignUp) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		return timestampSignUp.format(formatter);
	}

}
