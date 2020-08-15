package com.tasklist.util.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JwtException extends Exception{
	private static final Logger logger = LoggerFactory.getLogger(JwtException.class);
	private static final long serialVersionUID = 1L;
	
	private static final String DESCRPTION = "Error with JWT";
	
	public JwtException() {}
	
	public JwtException(String details) {
		super(details);
		logger.error("DESCRPTION: "+DESCRPTION + ". "+details);
	}
}
