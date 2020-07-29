package com.tasklist.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tasklist.dao.UserRepository;
import com.tasklist.dto.TaskRequest;
import com.tasklist.dto.UserRequest;
import com.tasklist.model.Task;
import com.tasklist.model.User;
import com.tasklist.util.exception.InternalServerErrorException;

@SpringBootTest
class TaskServiceTest {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TaskService taskService;
	
	private TaskRequest taskRequest;
	private UserRequest userRequest;
	private User user;
	
	@BeforeEach
	void afterEach() {	
		user = new User("Mat", "Dixon", "matdixon@gmail.com", "asd", 67898);
		userRepository.save(user);
		
		user = userRepository.findByEmail("matdixon@gmail.com");
		userRequest = new UserRequest(user.getId(), null, null, null, null, 0);
		taskRequest = new TaskRequest(0, "test2", "test2 - private", new Date(), false, userRequest, null);
	}
	
	@AfterEach
	void beforeEach() {
		user = userRepository.findByEmail("matdixon@gmail.com");
		userRepository.delete(user);
	}
	
	@Test
	void updateTaskTest() {	
		//generate task to update
		//--- Refactoring ---
		user = userRepository.findByEmail("matdixon@gmail.com");
		Task task = new Task("update task", "test update", new Date(), true);
		user.addTask(task);
		userRepository.save(user);
		
		user = userRepository.findByEmail("matdixon@gmail.com");
		task = user.getTasks().get(0);
		
		taskRequest.setId(task.getId());
		taskRequest.setTitle(task.getTitle());
		taskRequest.setDescription(task.getDescription());
		taskRequest.setStatus(task.isStatus());
		//-------
		
		//--REQUIRED
		taskRequest.setTitle(null);
		assertThrows(InternalServerErrorException.class, () -> taskService.updateTask(taskRequest));
		
		//--MAX FIELDS
		//title
		//101 characters
		taskRequest.setTitle("Lorem ipsum dolor sit amet consectetur adipiscing elit luctus at euismod tristique, metus nisi urna..");
		assertThrows(InternalServerErrorException.class, () -> taskService.updateTask(taskRequest));
		
		//description
		taskRequest.setTitle("update test");
		//256 characters
		taskRequest.setDescription("Lorem ipsum dolor sit amet consectetur adipiscing, elit in praesent dictum sagittis, pharetra cubilia felis risus nunc. Mattis mollis varius augue urna luctus sollicitudin litora donec, ac per justo sociis ligula a blandit, accumsan metus senectus sceler..");
		assertThrows(InternalServerErrorException.class, () -> taskService.updateTask(taskRequest));
				
		//---RIGHT WAY
		taskRequest.setDescription("test update");
		assertDoesNotThrow(()->taskService.updateTask(taskRequest));
	}

	@Test
	void storeAndDeleteTaskTest() {		
		//--REQUIRED
		taskRequest.setTitle(null);
		assertThrows(InternalServerErrorException.class, () -> taskService.storeTask(taskRequest));
		 
		//--MAX FIELDS
		//title
		//101 characters
		taskRequest.setTitle("Lorem ipsum dolor sit amet consectetur adipiscing elit luctus at euismod tristique, metus nisi urna..");
		assertThrows(InternalServerErrorException.class, () -> taskService.storeTask(taskRequest));
		
		//description
		taskRequest.setTitle("test2");
		//256 characters
		taskRequest.setDescription("Lorem ipsum dolor sit amet consectetur adipiscing, elit in praesent dictum sagittis, pharetra cubilia felis risus nunc. Mattis mollis varius augue urna luctus sollicitudin litora donec, ac per justo sociis ligula a blandit, accumsan metus senectus sceler..");
		assertThrows(InternalServerErrorException.class, () -> taskService.storeTask(taskRequest));
				
		//---RIGHT WAY
		//create
		taskRequest.setDescription("test2 - private");
		assertDoesNotThrow(()->taskService.storeTask(taskRequest));
		//delete
		long id = userRepository.findByEmail("matdixon@gmail.com").getTasks().get(0).getId();//refresh id
		assertDoesNotThrow(() -> taskService.deleteTask(id));
	}

}
