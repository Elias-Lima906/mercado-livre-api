package br.com.api.ml.category;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import br.com.api.ml.exception.GlobalException;
import br.com.api.ml.validation.UniqueValue;

public class CategoryRequestDTO {

	@NotBlank
	@UniqueValue(domainName = Category.class, fieldName = "name", message = "A Categoria já existe na base de dados!")
	private String name;

	@Nullable
	private Long idMotherCategory;

	public CategoryRequestDTO(@NotBlank String nome, @NotNull Long idCategory) {
		this.name = nome;
		this.idMotherCategory = idCategory;
	}

	public String getName() {
		return name;
	}

	public Long getIdCategory() {
		return idMotherCategory;
	}

	public Category toModel(EntityManager manager) throws GlobalException {

		Category category = new Category(this.name);
		Category motherCategory;

		if (this.idMotherCategory == null) {
			return category;
		}

		motherCategory = Category.findMotherCategory(this.idMotherCategory, manager);
		category.setCategory(motherCategory);
		
		return category;
	}

}
