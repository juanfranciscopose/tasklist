package com.tasklist.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tasklist.dto.UserRequest;
import com.tasklist.security.dto.Credentials;
import com.tasklist.security.dto.LoginRequest;
import com.tasklist.security.jwt.JwtProvider;
import com.tasklist.service.UserService;
import com.tasklist.util.exception.InternalServerErrorException;
import com.tasklist.util.exception.NotFoundException;
import com.tasklist.util.exception.UnprocessableEntityException;
import com.tasklist.util.validator.UserValidator;

@RestController
@RequestMapping("/auth")// - PERMIT ALL - 
@CrossOrigin //from any url
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@PostMapping("/login")//miss all validations
	public ResponseEntity<Credentials> login(@RequestBody LoginRequest loginRequest){
		Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(auth);
		String token = jwtProvider.generateToken(auth);
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		Credentials credentials = new Credentials(token, userDetails.getUsername(), userDetails.getAuthorities());
		return new ResponseEntity<>(credentials, HttpStatus.OK);		
	}
	
	@PostMapping("/users")
	public ResponseEntity<?> createUser(@RequestBody UserRequest user) throws UnprocessableEntityException, InternalServerErrorException, NotFoundException{
		userValidator.createValidator(user);
		userService.storeUser(user);
		return new ResponseEntity<>("created successfully", HttpStatus.CREATED);
	}
}
