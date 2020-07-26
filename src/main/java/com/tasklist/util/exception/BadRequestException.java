package com.tasklist.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	private static final String DESCRPTION = "The Url is wrong";
	
	public BadRequestException() {}
	
	public BadRequestException(String details) {
		super("DESCRPTION: "+DESCRPTION + ". "+details);
	}

}
