package com.tasklist.validator;

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
import com.tasklist.util.exception.UnprocessableEntityException;
import com.tasklist.util.validator.TaskValidator;

@SpringBootTest
class TaskValidatorTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TaskValidator taskValidator;
	
	private TaskRequest taskRequest;
	private UserRequest userRequest;
	
	@BeforeEach
	void afterEach() {	
		User user = new User("Mat", "Dixon", "matdixon@gmail.com", "asd", 67898);
		Task task = new Task("test1", "test1", new Date(), true);
		user.addTask(task);
		userRepository.save(user);
		
		user = userRepository.findByEmail("matdixon@gmail.com");
		userRequest = new UserRequest(user.getId(), null, null, null, null, 0);
		taskRequest = new TaskRequest(0, "test2", "test2", new Date(), false, userRequest, null);
	}
	
	@AfterEach
	void beforeEach() {
		User user = userRepository.findByEmail("matdixon@gmail.com");
		userRepository.delete(user);
	}
	
	@Test
	void createTaskTest() {
		//--MIN FIELDS: title
		//2 characters
		taskRequest.setTitle("as");
		assertThrows(UnprocessableEntityException.class, () -> taskValidator.createValidator(taskRequest));
		//3 characters
		taskRequest.setTitle("asd");
		assertDoesNotThrow(() -> taskValidator.createValidator(taskRequest));
		
		//--REQUIRED
		taskRequest.setTitle("");
		assertThrows(UnprocessableEntityException.class, () -> taskValidator.createValidator(taskRequest));
		taskRequest.setTitle(null);
		assertThrows(UnprocessableEntityException.class, () -> taskValidator.createValidator(taskRequest));
		
		//--MAX FIELDS
		//title
		//101 characters
		taskRequest.setTitle("Lorem ipsum dolor sit amet consectetur adipiscing elit luctus at euismod tristique, metus nisi urna..");
		assertThrows(UnprocessableEntityException.class, () -> taskValidator.createValidator(taskRequest));
		//100 characters
		taskRequest.setTitle("Lorem ipsum dolor sit amet consectetur adipiscing elit luctus at euismod tristique, metus nisi urna.");
		assertDoesNotThrow(() -> taskValidator.createValidator(taskRequest));
		
		//description
		taskRequest.setTitle("test2");
		//256 characters
		taskRequest.setDescription("Lorem ipsum dolor sit amet consectetur adipiscing, elit in praesent dictum sagittis, pharetra cubilia felis risus nunc. Mattis mollis varius augue urna luctus sollicitudin litora donec, ac per justo sociis ligula a blandit, accumsan metus senectus sceler..");
		assertThrows(UnprocessableEntityException.class, () -> taskValidator.createValidator(taskRequest));
		//255 characters
		taskRequest.setDescription("Lorem ipsum dolor sit amet consectetur adipiscing, elit in praesent dictum sagittis, pharetra cubilia felis risus nunc. Mattis mollis varius augue urna luctus sollicitudin litora donec, ac per justo sociis ligula a blandit, accumsan metus senectus sceler.");
		assertDoesNotThrow(() -> taskValidator.createValidator(taskRequest));
	}

}
