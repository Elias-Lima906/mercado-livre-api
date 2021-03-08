package br.com.api.ml.characteristc;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.com.api.ml.product.Product;

public class CharacteristicResponseDTO {

	private String name;

	private String description;

	@Deprecated
	public CharacteristicResponseDTO() {
	}
	
	public CharacteristicResponseDTO(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Characteristic toModel(@NotNull @Valid Product product) {
		return new Characteristic(name, description, product);
	}

}
