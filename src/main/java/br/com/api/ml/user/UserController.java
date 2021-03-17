package br.com.api.ml.user;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping
	@Transactional
	public UserResponseDTO postMethodName(@RequestBody @Valid UserRequestDTO request) {
		
		@Valid
		User user = request.toModel(passwordEncoder);
		manager.persist(user);
		return new UserResponseDTO("Úsuario cadastrado com sucesso!", user.getTimestampSignUp());
	}

}
