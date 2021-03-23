package br.com.api.ml.product.characteristc;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.api.ml.product.Product;

public class CharacteristicRequestDTO {

	@NotBlank
	private String name;

	@NotBlank
	@Size(max = 1000)
	private String description;

	@Deprecated
	public CharacteristicRequestDTO() {
	}

	public CharacteristicRequestDTO(String name, String description) {
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
