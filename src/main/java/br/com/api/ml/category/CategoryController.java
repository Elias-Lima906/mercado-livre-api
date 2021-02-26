package br.com.api.ml.category;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.ml.exception.GlobalException;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@PersistenceContext
	EntityManager manager;

	@PostMapping
	@Transactional
	public CategoryResponseDTO postMethodName(@RequestBody @Valid CategoryRequestDTO request) throws GlobalException {

		@Valid
		Category category = request.toModel(manager);
		manager.persist(category);
		return new CategoryResponseDTO(category);
	}

}
