package com.tasklist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tasklist.dto.ToDoRequest;
import com.tasklist.service.ToDoService;
import com.tasklist.util.exception.BadRequestException;
import com.tasklist.util.exception.InternalServerErrorException;
import com.tasklist.util.exception.NotFoundException;
import com.tasklist.util.exception.UnprocessableEntityException;
import com.tasklist.util.validator.ToDoValidator;

@RestController
@RequestMapping(path="/todo")
@CrossOrigin
public class ToDoController {
	
	@Autowired
	private ToDoValidator toDoValidator;
	
	@Autowired
	private ToDoService toDoService;
	
	@PostMapping()//tested with postman
	public ResponseEntity<?> createToDo(@RequestBody ToDoRequest toDo) throws UnprocessableEntityException, NotFoundException, InternalServerErrorException{
		this.toDoValidator.createValidator(toDo);
		toDoService.storeToDo(toDo);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")//tested with postman
	public ResponseEntity<?> removeToDo(@PathVariable("id") long id) throws BadRequestException, NotFoundException, InternalServerErrorException{
		this.toDoValidator.removeValidator(id);
		toDoService.deleteToDo(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping()//tested with postman
	public ResponseEntity<?> editToDo(@RequestBody ToDoRequest editToDo) throws UnprocessableEntityException, NotFoundException, BadRequestException, InternalServerErrorException{
		this.toDoValidator.editValidator(editToDo);
		toDoService.updateToDo(editToDo);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/status/{id}")//tested with postman
	public ResponseEntity<?> changeToDoStatus(@PathVariable("id") long id) throws BadRequestException, NotFoundException, InternalServerErrorException{
		this.toDoValidator.changeStatusValidator(id);
		toDoService.changeStatus(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
