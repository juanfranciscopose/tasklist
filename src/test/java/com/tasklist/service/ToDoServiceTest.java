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
import com.tasklist.dto.ToDoRequest;
import com.tasklist.model.Task;
import com.tasklist.model.User;
import com.tasklist.util.exception.InternalServerErrorException;

@SpringBootTest
class ToDoServiceTest {
	
	private Task task;
	private User user;
	private TaskRequest taskRequest= new TaskRequest();
	private ToDoRequest toDoRequest = new ToDoRequest();
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ToDoService toDoService;
	
	@BeforeEach
	void afterEach() {	
		user = new User("Mat", "Dixon", "matdixon@gmail.com", "asd", 67898);
		userRepository.save(user);		

		task = new Task("test1", "test1", new Date(), true);
		user.addTask(task);
		userRepository.save(user);		
	}
	
	@AfterEach
	void beforeEach() {
		user = userRepository.findByEmail("matdixon@gmail.com");
		userRepository.delete(user);
	}	
	
	@Test
	void storeAndDeleteToDoTest() {		
		//store
		user = userRepository.findByEmail("matdixon@gmail.com");
		taskRequest.setId(user.getTasks().get(0).getId());
		toDoRequest.setTask(taskRequest);
		toDoRequest.setTimeStamp(new Date());
		//256 characters
		toDoRequest.setDescription("Lorem ipsum dolor sit amet consectetur adipiscing, elit in praesent dictum sagittis, pharetra cubilia felis risus nunc. Mattis mollis varius augue urna luctus sollicitudin litora donec, ac per justo sociis ligula a blandit, accumsan metus senectus sceler..");
		assertThrows(InternalServerErrorException.class, () -> toDoService.storeToDo(toDoRequest));
		//255 characters
		toDoRequest.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque non nulla porttitor nulla fringilla convallis sed vitae ligula. Proin congue augue non sem consectetur tristique. Vestibulum viverra turpis et lorem lacinia, id congue justo erat curae.");
		assertDoesNotThrow(() -> toDoService.storeToDo(toDoRequest));
		
		//delete
		assertThrows(InternalServerErrorException.class, () ->toDoService.deleteToDo(0));
		long id = userRepository.findByEmail("matdixon@gmail.com").getTasks().get(0).getList().get(0).getId();
		assertDoesNotThrow(() -> toDoService.deleteToDo(id));
	}

}
