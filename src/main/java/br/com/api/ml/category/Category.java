package br.com.api.ml.category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import br.com.api.ml.exception.GlobalException;

@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false, unique = true)
	private String name;

	@ManyToOne
	private Category category;

	@Deprecated
	public Category() {
	}

	public Category(@NotBlank String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public static CategoryResponseDTO toCategoryDTO(Category category) {
		return new CategoryResponseDTO(category);
	}

	public static Category findMotherCategory(Long idMotherCategory, EntityManager manager) throws GlobalException {

		Category motherCategory = manager.find(Category.class, idMotherCategory);

		if (motherCategory == null) {
			throw new GlobalException("A Categoria de id: " + idMotherCategory
					+ " não existe, não foi possível adicionar a sua categoria a uma categoria mãe!");
		}

		return motherCategory;
	}
}
