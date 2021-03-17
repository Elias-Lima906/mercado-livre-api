package br.com.api.ml.purchase;

import java.util.Set;

import br.com.api.ml.product.ProductResponseDTO;
import br.com.api.ml.purchase.gateway.GatewayStatus;
import br.com.api.ml.purchase.transaction.Transaction;
import br.com.api.ml.purchase.transaction.TransactionResponseDTO;

public class PurchaseResponseDTO {

	private Integer amount;
	private PurchaseStatus status;
	private GatewayStatus gateway;
	private ProductResponseDTO product;
	private Set<TransactionResponseDTO> transactions;

	public PurchaseResponseDTO(Purchase purchase) {
		this.amount = purchase.getAmount();
		this.status = purchase.getStatus();
		this.gateway = purchase.getGateway();
		this.product = new ProductResponseDTO(purchase.getProduct());
		transactions = Transaction.toTransactionDTO(purchase.getTransactions());
	}

	public Integer getAmount() {
		return amount;
	}

	public PurchaseStatus getStatus() {
		return status;
	}

	public GatewayStatus getGateway() {
		return gateway;
	}

	public ProductResponseDTO getProduct() {
		return product;
	}

	public Set<TransactionResponseDTO> getTransactions() {
		return transactions;
	}

}
