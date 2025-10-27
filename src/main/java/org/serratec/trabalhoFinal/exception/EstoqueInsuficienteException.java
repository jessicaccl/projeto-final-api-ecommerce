package org.serratec.trabalhoFinal.exception;

// Estender IllegalArgumentException ou RuntimeException, a primeira é mais adequada para um BAD_REQUEST
public class EstoqueInsuficienteException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public EstoqueInsuficienteException(String message) {
		super(message);
	}
}