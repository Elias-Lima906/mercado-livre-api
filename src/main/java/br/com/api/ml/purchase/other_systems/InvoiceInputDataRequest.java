package br.com.api.ml.purchase.other_systems;

import javax.validation.constraints.NotNull;

public class InvoiceInputDataRequest {

	@NotNull
	private Long idPurchase;

	@NotNull
	private Long idBuyer;

	public InvoiceInputDataRequest(@NotNull Long idPurchase, @NotNull Long idBuyer) {
		this.idPurchase = idPurchase;
		this.idBuyer = idBuyer;
	}

	public Long getIdPurchase() {
		return idPurchase;
	}

	public Long getIdBuyer() {
		return idBuyer;
	}

}
