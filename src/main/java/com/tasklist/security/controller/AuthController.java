package com.tasklist.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tasklist.security.dto.Credential;
import com.tasklist.security.dto.LoginRequest;
import com.tasklist.security.service.LoginService;
import com.tasklist.security.validator.LoginValidator;
import com.tasklist.util.exception.UnprocessableEntityException;

@RestController
@RequestMapping("/auth")// - PERMIT ALL - 
@CrossOrigin //from any url
public class AuthController {
	@Autowired
	private LoginValidator loginValidator;
	
	@Autowired
	private LoginService loginService;
	
	@PostMapping("/login")//miss all validations
	public ResponseEntity<Credential> login(@RequestBody LoginRequest loginRequest) throws UnprocessableEntityException{
		loginValidator.loginValidator(loginRequest);
		Credential credentials = loginService.login(loginRequest);
		return new ResponseEntity<Credential>(credentials, HttpStatus.OK);		
	}
}
