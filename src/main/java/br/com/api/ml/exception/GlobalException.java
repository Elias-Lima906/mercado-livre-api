package br.com.api.ml.exception;

public class GlobalException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message;

	public GlobalException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
