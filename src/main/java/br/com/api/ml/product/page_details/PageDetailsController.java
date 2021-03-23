package br.com.api.ml.product.page_details;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.ml.product.Product;

@RestController
@RequestMapping("/details")
public class PageDetailsController {

	@PersistenceContext
	private EntityManager manager;

	@GetMapping("/{id}")
	public ProductPageDetails getDetailsPage(@PathVariable Long id) {
		Product product = Product.findProduct(manager, id);
		return new ProductPageDetails(product);
	}

}
