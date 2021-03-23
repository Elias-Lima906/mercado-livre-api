package br.com.api.ml.purchase.transaction;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.api.ml.purchase.Purchase;

@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private @NotBlank String idTransaction;

	@NotNull
	private StatusTransaction status;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime timestampCreation;

	@NotNull
	@Valid
	@JsonBackReference
	@ManyToOne
	private Purchase purchase;

	@Deprecated
	public Transaction() {
	}

	public Transaction(@NotNull StatusTransaction status, @NotBlank String idTransaction,
			@NotNull @Valid Purchase purchase) {
		this.status = status;
		this.idTransaction = idTransaction;
		this.purchase = purchase;
		timestampCreation = LocalDateTime.now();
	}

	public String getIdTransaction() {
		return idTransaction;
	}

	public StatusTransaction getStatus() {
		return status;
	}

	public LocalDateTime getTimestampCreation() {
		return timestampCreation;
	}

	public boolean successfullyCompleted() {
		return this.status.equals(StatusTransaction.sucesso);
	}

	public static Set<TransactionResponseDTO> toTransactionDTO(Set<Transaction> transactions) {
		return transactions.stream().map(transaction -> new TransactionResponseDTO(transaction)).collect(Collectors.toSet());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idTransaction == null) ? 0 : idTransaction.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (idTransaction == null) {
			if (other.idTransaction != null)
				return false;
		} else if (!idTransaction.equals(other.idTransaction))
			return false;
		return true;
	}

}
