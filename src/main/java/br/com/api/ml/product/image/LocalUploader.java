package br.com.api.ml.product.image;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Primary
public class LocalUploader implements Uploader {

	@Override
	public Set<String> getImageLinks(List<MultipartFile> images) {
		return images.stream().map(image -> "https://bucket.io/" + image.getOriginalFilename())
				.collect(Collectors.toSet());
	}
}
