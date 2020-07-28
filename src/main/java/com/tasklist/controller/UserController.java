package com.tasklist.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tasklist.dto.UserRequest;
import com.tasklist.service.UserService;
import com.tasklist.util.exception.BadRequestException;
import com.tasklist.util.exception.InternalServerErrorException;
import com.tasklist.util.exception.NotFoundException;
import com.tasklist.util.exception.UnprocessableEntityException;
import com.tasklist.util.validator.UserValidator;

@RestController
@RequestMapping(path="/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;
	
	@PostMapping()//tested with Postman
	public ResponseEntity<?> createUser(@RequestBody UserRequest user) throws UnprocessableEntityException, InternalServerErrorException{
		userValidator.createValidator(user);
		userService.storeUser(user);
		return new ResponseEntity<>("created successfully", HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")//tested with Postman
	public ResponseEntity<?> removeUser(@PathVariable("id") long id) throws BadRequestException, InternalServerErrorException, NotFoundException{
		userValidator.removeValidator(id);
		userService.deleteUser(id);
		return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
	}
}
