package br.com.api.ml.purchase.other_systems;

import javax.validation.constraints.NotNull;

public class RankingInputDataRequest {

	@NotNull
	private Long idPurchase;

	@NotNull
	private Long idOwner;

	public RankingInputDataRequest(@NotNull Long idPurchase, @NotNull Long idOwner) {
		this.idPurchase = idPurchase;
		this.idOwner = idOwner;
	}

	public Long getIdPurchase() {
		return idPurchase;
	}

	public Long getIdOwner() {
		return idOwner;
	}

}
