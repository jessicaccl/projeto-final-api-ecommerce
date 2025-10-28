package org.serratec.trabalhoFinal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EstoqueInsuficienteException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public EstoqueInsuficienteException(String message) {
		super(message);
	}
}