package br.com.api.ml.purchase.transaction;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.api.ml.purchase.Purchase;
import br.com.api.ml.purchase.gateway.GatewayPayment;
import br.com.api.ml.purchase.gateway.StatusPagseguro;

public class PagseguroRequestDTO implements GatewayPayment{

	@NotBlank
	private String idTransaction;

	@NotNull
	private StatusPagseguro status;

	public PagseguroRequestDTO(@NotBlank String idTransaction, @NotNull StatusPagseguro status) {
		this.idTransaction = idTransaction;
		this.status = status;
	}

	@Override
	public String toString() {
		return "PagseguroRquestDTO [idTransaction=" + idTransaction + ", status=" + status + "]";
	}

	public Transaction toTransaction(Purchase purchase) {
		// TODO Auto-generated method stub
		return new Transaction(status.normalizate(), idTransaction, purchase);
	}

}
