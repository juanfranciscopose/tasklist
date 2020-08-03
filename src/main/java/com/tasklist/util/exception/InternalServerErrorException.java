package com.tasklist.util.exception;
	
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends Exception{
	
	private static final Logger logger = LoggerFactory.getLogger(InternalServerErrorException.class);
	
	private static final long serialVersionUID = 1L;
	
	private static final String DESCRPTION = "The server has encountered a situation that it does not know how to handle.";
	
	public InternalServerErrorException() {}
	
	public InternalServerErrorException(String details) {
		super("DESCRPTION: "+DESCRPTION + ". "+details);
		logger.error("DESCRPTION: "+DESCRPTION + ". "+details);
		//System.out.println("DESCRPTION: "+DESCRPTION + ". "+details);
	}
}
