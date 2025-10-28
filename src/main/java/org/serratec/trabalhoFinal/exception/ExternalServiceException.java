package org.serratec.trabalhoFinal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExternalServiceException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ExternalServiceException(String message) { 
    	super(message); 
    }
}
