package br.com.api.ml.product.question;

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

import br.com.api.ml.mailer.Mailer;
import br.com.api.ml.product.Product;
import br.com.api.ml.product.ProductResponseDTO;
import br.com.api.ml.user.User;
import br.com.api.ml.user.UserRepository;

@RestController
@RequestMapping("/productQuestions")
public class QuestionController {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Mailer mailer;

	@PostMapping("/{id}")
	@Transactional
	public ProductResponseDTO postMethodName(@PathVariable Long id, @RequestBody @Valid QuestionRequestDTO request) {
		@Valid User user = User.findAuthenticatedUser(userRepository);
		@Valid Product product = Product.findProduct(manager, id);
		Question question = request.toModel(user, product);

		product.addQuestionToProduct(question);
		manager.merge(product);

		mailer.sendQuestion(question, product, user);
		return new ProductResponseDTO(product);
	}

}
