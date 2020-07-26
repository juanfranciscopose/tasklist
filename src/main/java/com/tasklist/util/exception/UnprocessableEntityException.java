package com.tasklist.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends Exception {

	private static final long serialVersionUID = 1L;

	private static final String DESCRPTION = "The received entity is not correct";
	
	public UnprocessableEntityException (String details) {
		super("DESCRPTION: "+DESCRPTION + ". "+details);
	}
}
