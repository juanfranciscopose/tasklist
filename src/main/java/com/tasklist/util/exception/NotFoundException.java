package com.tasklist.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public NotFoundException() {}
	
	public NotFoundException(String details) {
		super(details);
	}

}
