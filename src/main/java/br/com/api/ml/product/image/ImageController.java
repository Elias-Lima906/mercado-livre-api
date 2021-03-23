package br.com.api.ml.product.image;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.api.ml.product.Product;
import br.com.api.ml.product.ProductResponseDTO;
import br.com.api.ml.user.UserRepository;

@RestController
@RequestMapping("productImages")
public class ImageController {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	Uploader localUploader;

	@Autowired
	UserRepository userRepository;

	@PostMapping(path = "/{id}")
	@Transactional
	public ProductResponseDTO addImages(@Valid ImageRequestDTO request, @PathVariable Long id) {
		@Valid Product product = Product.findProduct(manager, id);
		product.addImages(localUploader.getImageLinks(request.getImages()));

		verifyIfUserIsProductOwner(product);

		manager.merge(product);
		return new ProductResponseDTO(product);
	}

	private void verifyIfUserIsProductOwner(Product product) {
		if (!product.userIsProductOwner(userRepository)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
					"Você está tentando adicionar imagens a um produto que não pertence a sua conta!");
		}
	}
}
