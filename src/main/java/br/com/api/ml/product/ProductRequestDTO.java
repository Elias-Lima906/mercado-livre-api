package br.com.api.ml.product;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.util.Assert;

import br.com.api.ml.category.Category;
import br.com.api.ml.product.characteristc.CharacteristicRequestDTO;
import br.com.api.ml.user.User;
import br.com.api.ml.user.UserRepository;
import br.com.api.ml.validation.ExistsById;

public class ProductRequestDTO {

	@NotBlank
	private String name;

	@NotNull
	@Positive
	private BigDecimal price;

	@NotNull
	@Min(0)
	private Integer availableQuantity;

	@NotBlank
	@Size(max = 1000)
	private String description;

	@NotNull
	@ExistsById(domainName = Category.class, fieldName = "id", message = "Id da categoria inexistente!")
	private Long idCategory;

	@Size(min = 3)
	List<CharacteristicRequestDTO> characteristics;

	@Deprecated
	public ProductRequestDTO() {
	}

	public ProductRequestDTO(@NotBlank String name, @NotNull @Positive BigDecimal price,
			@NotNull @Min(0) Integer availableQuantity, @NotBlank String description, @NotNull Long idCategory,
			@Size(min = 3) List<CharacteristicRequestDTO> characteristic) {
		this.name = name;
		this.price = price;
		this.availableQuantity = availableQuantity;
		this.description = description;
		this.idCategory = idCategory;
		this.characteristics = characteristic;
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

	public Long getIdCategory() {
		return idCategory;
	}

	public List<CharacteristicRequestDTO> getCharacteristics() {
		return characteristics;
	}

	public Product toModel(EntityManager manager, UserRepository userRepository) {

		Category category = manager.find(Category.class, idCategory);
		Assert.notNull(category, "Não foi encontado nenhuma categoria com o id: " + idCategory);

		User user = User.findAuthenticatedUser(userRepository);

		return new Product(name, price, availableQuantity, description, category, user, characteristics);
	}

	public boolean hasEqualCharacteristics() {
		HashSet<String> equalNames = new HashSet<String>();

		for (CharacteristicRequestDTO characteristic : characteristics) {
			if (!equalNames.add(characteristic.getName())) {
				return true;
			}
		}

		return false;
	}

}
