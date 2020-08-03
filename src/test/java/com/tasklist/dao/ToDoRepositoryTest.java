package com.tasklist.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tasklist.model.Task;
import com.tasklist.model.ToDo;
import com.tasklist.model.User;

@SpringBootTest
class ToDoRepositoryTest {
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ToDoRepository toDoRepository;
	
	private Task task;
	
	private User user;

	private ToDo toDo;
	
	@BeforeEach
	public void before() {
		user = new User("Mat", "Dixon", "matdixon@gmail.com", "asd", 1234);
		
		task = new Task("Spring boot practice", "class 1", new Date(), false);
		toDo = new ToDo("Spring Data + Spring MVC", new Date());
		task.addToDo(toDo);
		user.addTask(task);
		
		userRepository.save(user);
	}
	
	@AfterEach
	public void after() {
		userRepository.delete(userRepository.findByEmail(user.getEmail()).get());
	}
	@Test
	void updateToDoTest() {
		//check initial state (only one 'to do' on list)
		user = userRepository.findByEmail(user.getEmail()).get();
		assertEquals(1, user.getTasks().size());
		task = user.getTasks().get(0);
		assertEquals(1, task.getList().size());
		
		//add 'to do' on task
		task = taskRepository.findById(task.getId()).get();
		task.addToDo(new ToDo("test1", new Date()));
		taskRepository.save(task);
		
		//check list again
		task = taskRepository.findById(task.getId()).get();
		assertEquals(2, task.getList().size());
		
		//remove 'to do'
		task = taskRepository.findById(task.getId()).get();
		ToDo toDo1 = toDoRepository.findById(task.getList().get(0).getId()).get();
		task.removeToDo(toDo1);
		taskRepository.save(task);
		
		//check list again
		task = taskRepository.findById(task.getId()).get();
		assertEquals(1, task.getList().size());
		
		//update description
		task = taskRepository.findById(task.getId()).get();
		toDo1 = toDoRepository.findById(task.getList().get(0).getId()).get();
		toDo1.setDescription("change description test");
		toDoRepository.save(toDo1);
		
		//check description
		task = taskRepository.findById(task.getId()).get();
		toDo1 = toDoRepository.findById(task.getList().get(0).getId()).get();
		assertEquals("change description test", toDo1.getDescription());
		
		//update status
		task = taskRepository.findById(task.getId()).get();
		toDo1 = toDoRepository.findById(task.getList().get(0).getId()).get();
		assertFalse(toDo1.isStatus());
		toDo1.setStatus(true);
		toDoRepository.save(toDo1);
		
		//check status
		task = taskRepository.findById(task.getId()).get();
		toDo1 = toDoRepository.findById(task.getList().get(0).getId()).get();
		assertTrue(toDo1.isStatus());
	}

}
