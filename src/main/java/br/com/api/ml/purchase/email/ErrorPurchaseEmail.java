package br.com.api.ml.purchase.email;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.api.ml.mailer.Mailer;
import br.com.api.ml.product.Product;
import br.com.api.ml.product.question.Question;
import br.com.api.ml.purchase.Purchase;
import br.com.api.ml.purchase.gateway.GatewayStatus;
import br.com.api.ml.user.User;

@Component
public class ErrorPurchaseEmail implements Mailer {

	@Override
	public void sendQuestion(@NotNull @Valid Question question, @NotNull @Valid Product product,
			@NotNull @Valid User user) {
	}

	@Override
	public void send(Purchase purchase) {

		String UriRedirect = checkGatewayStatus(purchase);

		System.out.println("\n\nfrom: " + purchase.getProductOwnerEmail());
		System.out.println("to: " + purchase.getBuyerEmail());
		System.out.println("Compra não processada, por favor refaça a operação.");
		System.out.println("link para realização de nova tentativa: " + UriRedirect + "\n\n");

	}

	private String checkGatewayStatus(Purchase purchase) {
		GatewayStatus gateway = purchase.getGateway();
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();

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
