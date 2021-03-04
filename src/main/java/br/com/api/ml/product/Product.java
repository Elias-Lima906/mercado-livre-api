package br.com.api.ml.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.api.ml.category.Category;
import br.com.api.ml.characteristc.Characteristic;
import br.com.api.ml.characteristc.CharacteristicDTO;
import br.com.api.ml.user.User;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String name;

	@NotNull
	@Positive
	@Column(nullable = false)
	private BigDecimal price;

	@NotNull
	@Min(0)
	@Column(nullable = false)
	private Integer availableQuantity;

	@NotBlank
	@Column(nullable = false, length = 1000)
	private String description;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime timestampSignUp = LocalDateTime.now();

	@NotNull
	@ManyToOne
	private Category category;

	@JsonManagedReference
	@OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
	Set<Characteristic> characteristics = new HashSet<Characteristic>();

	@ManyToOne
	private User user;
	
	@Deprecated
	public Product() {
	}

	public Product(@NotBlank String name, @NotNull @Positive BigDecimal price,
			@NotNull @Min(0) Integer availableQuantity, @NotBlank String description, @NotNull Category category,
			@NotNull @Valid User user, @Size(min = 3) Collection<CharacteristicDTO> characteristics) {
		this.name = name;
		this.price = price;
		this.availableQuantity = availableQuantity;
		this.description = description;
		this.category = category;
		this.user = user;
		this.characteristics.addAll(addCharacteristics(characteristics));
	}

	private Set<Characteristic> addCharacteristics(Collection<CharacteristicDTO> characteristicsDto) {
		return characteristicsDto.stream().map(characteristic -> characteristic.toModel(this))
				.collect(Collectors.toSet());
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

	public LocalDateTime getTimestampSignUp() {
		return timestampSignUp;
	}

	public Category getCategory() {
		return category;
	}

	public Set<Characteristic> getCharacteristics() {
		return characteristics;
	}

}
