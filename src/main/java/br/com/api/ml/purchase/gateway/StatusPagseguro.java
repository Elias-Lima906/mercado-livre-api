package br.com.api.ml.purchase.gateway;

import br.com.api.ml.purchase.transaction.StatusTransaction;

public enum StatusPagseguro {
	SUCESSO, ERRO;

	public StatusTransaction normalizate() {
		
		if(this.equals(SUCESSO)) {
			return StatusTransaction.sucesso;
		}
		
		return StatusTransaction.erro;
	}
}
