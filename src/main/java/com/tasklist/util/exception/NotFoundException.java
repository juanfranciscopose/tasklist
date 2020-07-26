package com.tasklist.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private static final String DESCRPTION = "The server could not find the requested content";
	
	public NotFoundException() {}
	
	public NotFoundException(String details) {
		super("DESCRPTION: "+DESCRPTION + ". "+details);
	}

}
