package br.com.api.ml.product.image;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import br.com.api.ml.product.Product;

@Entity
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne
	@Valid
	private Product product;

	@URL
	@NotBlank
	private String link;

	@Deprecated
	public Image() {
	}

	public Image(@NotNull @Valid Product product, @URL @NotBlank String link) {
		this.product = product;
		this.link = link;
	}

	public Long getId() {
		return id;
	}

	public String getLink() {
		return link;
	}

	public static Set<ImageResponseDTO> toImageResponseDTO(Set<Image> images) {
		return images.stream().map(image -> new ImageResponseDTO(image)).collect(Collectors.toSet());
	}

	public void checkIfHasEqualImages(Set<Image> images, String link) {
		if (images.contains(this)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma imagem adicionada pelo úsuario: "
					+ this.product.getOwnerEmail() + " a este produto, com o nome: " + link.substring(18) + ".");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Image other = (Image) obj;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}

}