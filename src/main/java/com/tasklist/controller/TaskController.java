package com.tasklist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tasklist.dto.TaskRequest;
import com.tasklist.dto.ToDoRequest;
import com.tasklist.service.TaskService;
import com.tasklist.util.exception.BadRequestException;
import com.tasklist.util.exception.InternalServerErrorException;
import com.tasklist.util.exception.NotFoundException;
import com.tasklist.util.exception.UnprocessableEntityException;
import com.tasklist.util.validator.TaskValidator;

@RestController
@CrossOrigin
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
		return new ResponseEntity<>(HttpStatus.OK);
	}	
	
	@DeleteMapping("/{id}")//tested with Postman
	public ResponseEntity<?> removeTask(@PathVariable("id") long id) throws BadRequestException, NotFoundException, InternalServerErrorException{
		this.taskValidator.deleteValidator(id);
		taskService.deleteTask(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//endpoint for authenticated user index page - task without 'to do' list
	@GetMapping()//tested with Postman
	public ResponseEntity<?> getAllPublicTask() throws InternalServerErrorException {
		List<TaskRequest> list = taskService.getAllPublicTask();
		if(! list.isEmpty()) {
			return new ResponseEntity<List<TaskRequest>>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);//204
	}
	
	@PutMapping()//tested
	public ResponseEntity<?> editTask(@RequestBody TaskRequest editTask) throws UnprocessableEntityException, NotFoundException, InternalServerErrorException{
		this.taskValidator.updateValidator(editTask);
		taskService.updateTask(editTask);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//(for index) - return all 'to do' for a given task - needs in public task 
	@GetMapping("/{task_id}/todo")//tested
	public ResponseEntity<?> getAllTaskToDo(@PathVariable("task_id") long taskId) throws NotFoundException, InternalServerErrorException, BadRequestException {	
		this.taskValidator.getAllTaskToDoValidator(taskId);
		List<ToDoRequest> list = taskService.getAllToDo(taskId);
		if(! list.isEmpty()) {
			return new ResponseEntity<List<ToDoRequest>>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);//204
	}
	
	@GetMapping("/{task_id}")
	public ResponseEntity<TaskRequest> getTask(@PathVariable("task_id") long taskId) throws NotFoundException, InternalServerErrorException, BadRequestException {	
		this.taskValidator.getTaskValidator(taskId);
		TaskRequest task = taskService.getTask(taskId);
		return new ResponseEntity<TaskRequest>(task, HttpStatus.OK);
	}
}
