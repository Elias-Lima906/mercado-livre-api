package br.com.api.ml.product.characteristc;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.api.ml.product.Product;

@Entity
public class Characteristic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String name;

	@NotBlank
	@Size(max = 1000)
	@Column(nullable = false, length = 1000)
	private String description;

	@NotNull
	@JsonBackReference
	@ManyToOne
	private Product product;

	@Deprecated
	public Characteristic() {
	}

	public Characteristic(@NotBlank String name, @NotBlank @Size(max = 1000) String description,
			@NotNull @Valid Product product) {
		this.name = name;
		this.description = description;
		this.product = product;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Product getProduct() {
		return product;
	}

	public static Set<CharacteristicResponseDTO> toCharacteristicDTO(Set<Characteristic> characteristics) {
		return characteristics.stream().map(characteristic -> new CharacteristicResponseDTO(characteristic))
				.collect(Collectors.toSet());
	}

}
