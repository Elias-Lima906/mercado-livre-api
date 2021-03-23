package br.com.api.ml.purchase.other_systems;

import br.com.api.ml.purchase.Purchase;

public interface SuccessPurchaseEvent {
	
	void process(Purchase purchase);
}
