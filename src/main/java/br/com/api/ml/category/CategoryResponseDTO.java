package br.com.api.ml.category;

public class CategoryResponseDTO {

	private String name;
	private Category parentCategory;

	public CategoryResponseDTO(Category category) {
		this.name = category.getName();
		this.parentCategory = category.getCategory();
	}

	public String getName() {
		return name;
	}

	public Category getParentCategory() {
		return parentCategory;
	}

}
