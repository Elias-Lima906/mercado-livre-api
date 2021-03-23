package br.com.api.ml.product.opinion;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.ml.product.Product;
import br.com.api.ml.product.ProductResponseDTO;
import br.com.api.ml.user.User;
import br.com.api.ml.user.UserRepository;

@RestController
@RequestMapping("/productOpinions")
public class OpinionController {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/{id}")
	@Transactional
	public ProductResponseDTO addOpinions(@PathVariable Long id, @RequestBody @Valid OpinionRequestDTO request) {
		@Valid User user = User.findAuthenticatedUser(userRepository);
		@Valid Product product = Product.findProduct(manager, id);
		@Valid Opinion opinion = request.toModel(user, product);
		
		product.addOpinionToProduct(opinion);
		
		manager.merge(product);
		
		return new ProductResponseDTO(product);
	}

}
