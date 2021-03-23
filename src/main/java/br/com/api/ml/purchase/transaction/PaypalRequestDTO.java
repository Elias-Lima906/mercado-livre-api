package br.com.api.ml.purchase.transaction;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import br.com.api.ml.purchase.Purchase;
import br.com.api.ml.purchase.gateway.GatewayPayment;

public class PaypalRequestDTO implements GatewayPayment {

	@Min(0)
	@Max(1)
	private int status;

	@NotBlank
	private String idTransaction;

	public PaypalRequestDTO(@Min(0) @Max(1) int status, @NotBlank String idTransaction) {
		this.status = status;
		this.idTransaction = idTransaction;
	}

	public Transaction toTransaction(Purchase purchase) {

		StatusTransaction status = this.status == 0 ? StatusTransaction.erro : StatusTransaction.sucesso;

		return new Transaction(status, idTransaction, purchase);
	}

}
