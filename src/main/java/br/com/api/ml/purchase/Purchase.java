package br.com.api.ml.purchase;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.api.ml.product.Product;
import br.com.api.ml.purchase.gateway.GatewayPayment;
import br.com.api.ml.purchase.gateway.GatewayStatus;
import br.com.api.ml.purchase.transaction.Transaction;
import br.com.api.ml.user.User;

@Entity
public class Purchase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Positive
	@Column(nullable = false)
	private Integer amount;

	private PurchaseStatus status = PurchaseStatus.INICIADA;

	@Enumerated(EnumType.STRING)
	private GatewayStatus gateway;

	@ManyToOne
	@NotNull
	@Valid
	private Product product;

	@ManyToOne
	@NotNull
	@Valid
	private User buyer;

	@JsonManagedReference
	@OneToMany(mappedBy = "purchase", cascade = CascadeType.MERGE)
	private Set<Transaction> transactions;

	@Deprecated
	public Purchase() {
	}

	public Purchase(@Positive @NotNull Integer amount, @NotBlank GatewayStatus gateway, Product product, User buyer) {
		this.amount = amount;
		this.gateway = gateway;
		this.product = product;
		this.buyer = buyer;
	}

	public Long getId() {
		return id;
	}

	public Integer getAmount() {
		return amount;
	}

	public PurchaseStatus getStatus() {
		return status;
	}
	
	public void setStatus(PurchaseStatus status) {
		this.status = status;
	}

	public Long getBuyerId() {
		return buyer.getId();
	}

	public GatewayStatus getGateway() {
		return gateway;
	}

	public Product getProduct() {
		return product;
	}

	public String getBuyerEmail() {
		return buyer.getEmail();
	}

	public Long getProductOwnerId() {
		return product.getOwnerId();
	}

	public String getProductOwnerEmail() {
		return product.getOwnerEmail();
	}

	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void addTransaction(@Valid GatewayPayment request) {
		Transaction transaction = request.toTransaction(this);
		Assert.isTrue(!this.transactions.contains(transaction), 
				"Já existe uma transaão com as mesmas informações!");

		Assert.isTrue(successfullyCompletedTransactions().isEmpty(),
				"Esta compra já está com status de concluida com sucesso!");

		this.transactions.add(transaction);
	}

	private Set<Transaction> successfullyCompletedTransactions() {
		Set<Transaction> successfullyCompletedTransactions = this.transactions.stream()
				.filter(Transaction::successfullyCompleted).collect(Collectors.toSet());

		Assert.isTrue(successfullyCompletedTransactions.size() <= 1,
				"Há mais de uma transação concluida com sucesso para esta mesma compra!");
		return successfullyCompletedTransactions;
	}

	public boolean successfullyProcessed() {
		return !successfullyCompletedTransactions().isEmpty();
	}

}
