package br.com.api.ml.purchase;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.ml.mailer.Mailer;
import br.com.api.ml.purchase.email.ErrorPurchaseEmail;
import br.com.api.ml.purchase.email.SuccessPurchaseEmail;
import br.com.api.ml.purchase.other_systems.SuccessPurchaseEvent;

@Service
public class NewPurchaseEvents {
	
	@Autowired
	private Mailer mailer;

	@Autowired
	private Set<SuccessPurchaseEvent> successPurchaseEvent;

	public void process(Purchase purchase) {
		if (purchase.successfullyProcessed()) {
			mailer = new SuccessPurchaseEmail();
			successPurchaseEvent.forEach(event -> event.process(purchase));
			mailer.send(purchase);
			return;
		}
		
		mailer = new ErrorPurchaseEmail();
		mailer.send(purchase);
	}

}
