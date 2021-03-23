package br.com.api.ml.purchase.gateway;

import br.com.api.ml.purchase.Purchase;
import br.com.api.ml.purchase.transaction.Transaction;

public interface GatewayPayment {

	Transaction toTransaction(Purchase purchase);
}
