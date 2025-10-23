package org.serratec.trabalhoFinal.exception;

public class SaldoInsuficienteException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public SaldoInsuficienteException(String message) {
		super(message);
	}
}