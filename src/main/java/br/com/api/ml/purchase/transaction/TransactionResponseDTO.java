package br.com.api.ml.purchase.transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransactionResponseDTO {

	private String idTransaction;
	private StatusTransaction status;
	private String timestampCreation;

	public TransactionResponseDTO(Transaction transaction) {
		this.idTransaction = transaction.getIdTransaction();
		this.status = transaction.getStatus();
		this.timestampCreation = convertToString(transaction.getTimestampCreation());
	}

	public String getIdTransaction() {
		return idTransaction;
	}

	public StatusTransaction getStatus() {
		return status;
	}

	public String getTimestampCreation() {
		return timestampCreation;
	}

	private String convertToString(LocalDateTime timestampSignUp) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		return timestampSignUp.format(formatter);
	}
}
