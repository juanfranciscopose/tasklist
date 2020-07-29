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

import com.tasklist.dto.TaskRequest;
import com.tasklist.service.TaskService;
import com.tasklist.util.exception.BadRequestException;
import com.tasklist.util.exception.InternalServerErrorException;
import com.tasklist.util.exception.NotFoundException;
import com.tasklist.util.exception.UnprocessableEntityException;
import com.tasklist.util.validator.TaskValidator;

@RestController
@RequestMapping(path="/tasks")
public class TaskController {
	
	@Autowired
	private TaskValidator taskValidator;
	@Autowired
	private TaskService taskService;
	
	@PostMapping()//tested with Postman
	public ResponseEntity<?> createTask(@RequestBody TaskRequest task) throws UnprocessableEntityException, NotFoundException, InternalServerErrorException{
		taskValidator.createValidator(task);
		taskService.storeTask(task);
		return new ResponseEntity<>("created successfully", HttpStatus.OK);
	}	
	
	@DeleteMapping("/{id}")//tested with Postman
	public ResponseEntity<?> removeTask(@PathVariable("id") long id) throws BadRequestException, NotFoundException, InternalServerErrorException{
		this.taskValidator.deleteValidator(id);
		taskService.deleteTask(id);
		return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
	}
}
