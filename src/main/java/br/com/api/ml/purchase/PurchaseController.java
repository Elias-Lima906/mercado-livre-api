package br.com.api.ml.purchase;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.api.ml.product.Product;
import br.com.api.ml.purchase.gateway.GatewayStatus;
import br.com.api.ml.user.UserRepository;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

	@PersistenceContext
	private EntityManager manager;
	@Autowired
	private UserRepository userRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<String> savePurchase(@RequestBody PurchaseRequestDTO request,
			UriComponentsBuilder uriComponentsBuilder) throws BindException {
		Product product = Product.findProduct(manager, request.getIdProduct());
		Purchase purchase = null;
		boolean wroteOff = product.writeOffStock(request.getAmount());

		if (wroteOff) {
			purchase = request.toModel(userRepository, product);
			manager.persist(purchase);
			String UriRedirect = checkGatewayStatus(purchase, uriComponentsBuilder);
			return ResponseEntity.ok(UriRedirect);
		}

		BindException stocksProblem = new BindException(request, "purchaseRequestDTO");
		stocksProblem.reject(null, "A compra não foi concluida por problemas no estoque!");
		throw stocksProblem;
	}

	private String checkGatewayStatus(Purchase purchase, UriComponentsBuilder uriComponentsBuilder) {
		GatewayStatus gateway = purchase.getGateway();

		if (gateway.equals(GatewayStatus.PAYPAL)) {
			return UrlPaypal(uriComponentsBuilder, purchase);
		} else {
			return UrlPagseguro(uriComponentsBuilder, purchase);
		}
	}

	private String UrlPaypal(UriComponentsBuilder uriComponentsBuilder, Purchase purchase) {
		String UrlPaypal = uriComponentsBuilder.path("/retorno-paypal/{id}").buildAndExpand(purchase.getId())
				.toString();
		return "paypal.com/" + purchase.getId() + "?redirectUrl=" + UrlPaypal;
	}

	private String UrlPagseguro(UriComponentsBuilder uriComponentsBuilder, Purchase purchase) {
		String UrlPagseguro = uriComponentsBuilder.path("/retorno-pagseguro/{id}").buildAndExpand(purchase.getId())
				.toString();
		return "pagseguro.com/" + purchase.getId() + "&redirectUrl=" + UrlPagseguro;
	}

}
