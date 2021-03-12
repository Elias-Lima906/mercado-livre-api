package br.com.api.ml.product.image;

import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

public interface Uploader {
	
	public Set<String> getImageLinks(List<MultipartFile> images);

}
