package br.com.api.ml.purchase.other_systems;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OtherSystemController {

	@PostMapping(path = "/invoices")
	public void createInvoices(@RequestBody @Valid InvoiceInputDataRequest request) throws InterruptedException {
		System.out.println("Criando nota para compra de id: " + request.getIdPurchase() + " do comprador de id: "
				+ request.getIdBuyer());
		Thread.sleep(150);
	}

	@PostMapping(path = "/ranking")
	public void createRanking(@RequestBody @Valid RankingInputDataRequest request) throws InterruptedException {
		System.out.println("Criando nota para compra de id: " + request.getIdPurchase()
				+ " vendido pelo úsuario de id: " + request.getIdOwner());
		Thread.sleep(150);
	}

}
