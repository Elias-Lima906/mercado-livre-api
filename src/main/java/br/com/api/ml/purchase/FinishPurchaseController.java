package br.com.api.ml.purchase;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.ml.purchase.gateway.GatewayPayment;
import br.com.api.ml.purchase.transaction.PagseguroRequestDTO;
import br.com.api.ml.purchase.transaction.PaypalRequestDTO;

@RestController
public class FinishPurchaseController {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private NewPurchaseEvents newPurchaseEvents;

	@PostMapping("/retorno-pagseguro/{id}")
	@Transactional
	public PurchaseStatus proccessPagseguro(@PathVariable("id") Long idPurchase,
			@Valid PagseguroRequestDTO request) {
		return proccessPayment(idPurchase, request);
	}

	@PostMapping("/retorno-paypal/{id}")
	@Transactional
	public PurchaseStatus proccessPaypal(@PathVariable("id") Long idPurchase, @Valid PaypalRequestDTO request) {
		return proccessPayment(idPurchase, request);
	}

	private PurchaseStatus proccessPayment(Long idPurchase, @Valid GatewayPayment request) {
		Purchase purchase = manager.find(Purchase.class, idPurchase);
		purchase.addTransaction(request);
		purchase.setStatus(PurchaseStatus.FINALIZADA);

		manager.merge(purchase);
		newPurchaseEvents.process(purchase);
		
		return purchase.getStatus();
	}

}
