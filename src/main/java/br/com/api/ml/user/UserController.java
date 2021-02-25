package br.com.api.ml.user;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	@PersistenceContext
	private EntityManager manager;

	@PostMapping
	@Transactional
	public UserResponseDTO postMethodName(@RequestBody @Valid UserRequestDTO request) {
		
		@Valid
		User user = request.toModel();
		manager.persist(user);
		return new UserResponseDTO(user);
	}

}
