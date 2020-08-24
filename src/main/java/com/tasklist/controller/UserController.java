package com.tasklist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tasklist.dto.TaskRequest;
import com.tasklist.dto.UserRequest;
import com.tasklist.service.UserService;
import com.tasklist.util.exception.BadRequestException;
import com.tasklist.util.exception.InternalServerErrorException;
import com.tasklist.util.exception.NotFoundException;
import com.tasklist.util.exception.UnprocessableEntityException;
import com.tasklist.util.validator.UserValidator;

@RestController
@RequestMapping
@CrossOrigin
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;
	
	@PostMapping("/auth/users")// - PERMIT ALL - 
	public ResponseEntity<?> createUser(@RequestBody UserRequest user) throws UnprocessableEntityException, InternalServerErrorException, NotFoundException{
		userValidator.createValidator(user);
		userService.storeUser(user);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/users/{id}")//tested with Postman
	public ResponseEntity<?> removeUser(@PathVariable("id") long id) throws BadRequestException, InternalServerErrorException, NotFoundException{
		userValidator.removeValidator(id);
		userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//return all task for a given user
	@GetMapping("/users/{user_id}/tasks")//tested
	public ResponseEntity<?> getAllUserTask(@PathVariable("user_id") long userId) throws BadRequestException, InternalServerErrorException, NotFoundException {
		userValidator.getAllUserTaskValidator(userId);
		List<TaskRequest> list = userService.getAllUserTask(userId);
		if(! list.isEmpty()) {
			return new ResponseEntity<List<TaskRequest>>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);//204
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<UserRequest>> getAllUsers() throws InternalServerErrorException {
		List<UserRequest> list = userService.getAllUsers();
		if(! list.isEmpty()) {
			return new ResponseEntity<List<UserRequest>>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);//204
	}
}
