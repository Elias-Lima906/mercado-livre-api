package br.com.api.ml.product.image;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

public class ImageRequestDTO {

	@Size(min = 1)
	@NotNull
	List<MultipartFile> images = new ArrayList<MultipartFile>();

	@Deprecated
	public ImageRequestDTO() {
	}

	public ImageRequestDTO(@Size(min = 1) List<MultipartFile> images) {
		this.images = images;
	}

	public void setImages(List<MultipartFile> images) {
		this.images = images;
	}

	public List<MultipartFile> getImages() {
		return images;
	}

}
