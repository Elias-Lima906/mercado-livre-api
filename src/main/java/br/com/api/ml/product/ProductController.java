package br.com.api.ml.product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.ml.user.UserRepository;

@RestController
@RequestMapping("/products")
public class ProductController {

	@PersistenceContext
	EntityManager manager;

	@Autowired
	UserRepository userRepository;

	@InitBinder(value = "ProductRequestDTO")
	public void init(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(new prohibitCharacteristicWithSameNameValidator());
	}

	@PostMapping
	@Transactional
	public ProductResponseDTO saveProduct(@RequestBody @Valid ProductRequestDTO request) {
		@Valid Product product = request.toModel(manager, userRepository);
		manager.persist(product);
		return new ProductResponseDTO(product);
	}	

}
