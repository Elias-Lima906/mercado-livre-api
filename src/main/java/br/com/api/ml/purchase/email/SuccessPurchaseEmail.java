package br.com.api.ml.purchase.email;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.com.api.ml.mailer.Mailer;
import br.com.api.ml.product.Product;
import br.com.api.ml.product.question.Question;
import br.com.api.ml.purchase.Purchase;
import br.com.api.ml.user.User;

public class SuccessPurchaseEmail implements Mailer {

	@Override
	public void send(Purchase purchase) {
		BigDecimal price = toBigDecimal(purchase);

		System.out.println("\n\nfrom: " + purchase.getProductOwnerEmail());
		System.out.println("to: " + purchase.getBuyerEmail());
		System.out.println("Compra Realizada com sucesso!");
		System.out.println("Id da compra: " + purchase.getId());
		System.out.println("Quantidade da compra: " + purchase.getAmount());
		System.out.println("Método de pagamento : " + purchase.getGateway());
		System.out.println("Nome do produto : " + purchase.getProduct().getName());
		System.out.println("Preço do produto : " + price + "\n\n");
	}

	private BigDecimal toBigDecimal(Purchase purchase) {
		return purchase.getProduct().getPrice().multiply(BigDecimal.valueOf(purchase.getAmount()));
	}

	@Override
	public void sendQuestion(@NotNull @Valid Question question, @NotNull @Valid Product product,
			@NotNull @Valid User user) {
	}

}
