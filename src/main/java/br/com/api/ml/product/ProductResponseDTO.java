package br.com.api.ml.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import br.com.api.ml.category.Category;
import br.com.api.ml.characteristc.Characteristic;

public class ProductResponseDTO {

	private String name;
	private BigDecimal price;
	private Integer availableQuantity;
	private String description;
	private String timestampSignUp;
	private Category category;
	Set<Characteristic> characteristics;

	@Deprecated
	public ProductResponseDTO() {
	}

	public ProductResponseDTO(Product product) {
		this.name = product.getName();
		this.price = product.getPrice();
		this.availableQuantity = product.getAvailableQuantity();
		this.description = product.getDescription();
		this.timestampSignUp = this.convertToString(product.getTimestampSignUp());
		this.category = product.getCategory();
		this.characteristics = product.getCharacteristics();
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

	public Category getCategory() {
		return category;
	}

	public Set<Characteristic> getCharacteristics() {
		return characteristics;
	}

	private String convertToString(LocalDateTime timestampSignUp) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

		String formatDateTime = timestampSignUp.format(formatter);

		return formatDateTime;
	}

}
