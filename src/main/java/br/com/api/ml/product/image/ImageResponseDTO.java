package br.com.api.ml.product.image;

public class ImageResponseDTO {

	private String link;

	@Deprecated
	public ImageResponseDTO() {
	}

	public ImageResponseDTO(Image image) {
		this.link = image.getLink();
	}

	public String getLink() {
		return link;
	}

}