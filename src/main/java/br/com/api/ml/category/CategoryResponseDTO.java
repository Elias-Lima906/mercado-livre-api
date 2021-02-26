package br.com.api.ml.category;

public class CategoryResponseDTO {

	private String nome;

	private Category parentCategory;

	public CategoryResponseDTO(Category category) {
		this.nome = category.getName();
		this.parentCategory = category.getCategory();
	}

	public String getNome() {
		return nome;
	}

	public Category getIdCategory() {
		return parentCategory;
	}

}
