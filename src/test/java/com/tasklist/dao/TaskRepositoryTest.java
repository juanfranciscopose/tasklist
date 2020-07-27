package com.tasklist.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tasklist.model.Task;
import com.tasklist.model.User;

@SpringBootTest
class TaskRepositoryTest {
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private Task task1;
	private Task task2;
	
	private User user1;
	private User user2;
	
	@BeforeEach
	public void before() {
		user1 = new User("Jhon", "Doe", "jhondoe@gmail.com", "asd", 1234);
		user2 = new User("Mat", "Dixon", "matdixon@gmail.com", "asd", 1234);

		task1 = new Task("Tasklist proyect", "inspired by trelo", new Date(), true);
		user1.addTask(task1);
		task2 = new Task("Spring boot practice", "class 1", new Date(), false);
		user2.addTask(task2);
		
		userRepository.save(user1);
		userRepository.save(user2);
	}
	
	@AfterEach
	public void after() {
		userRepository.delete(userRepository.findByEmail(user1.getEmail()));
		userRepository.delete(userRepository.findByEmail(user2.getEmail()));
	}
	
	@Test
	void findByStatusFalseTest() {
		//check initial state (only one task on list)
		List<Task> listStatusFalse = taskRepository.findByStatus(false);
		assertEquals(1, listStatusFalse.size());
		
		//add other task to list
		user1 = userRepository.findByEmail(user1.getEmail());
		assertEquals(1, user1.getTasks().size());
		Task task3 = new Task("test2", "test2", new Date(), false);
		user1.addTask(task3);
		userRepository.save(user1);
		
		//check task list again
		user1 = userRepository.findByEmail(user1.getEmail());
		assertEquals(2, user1.getTasks().size());
		listStatusFalse = taskRepository.findByStatus(false);
		assertEquals(2, listStatusFalse.size());
		
		//delete one task
		user1 = userRepository.findByEmail(user1.getEmail());
		Task task4 = user1.getTasks().get(1);
		user1.removeTask(task4);
		userRepository.save(user1);
		
		//check task list again
		user1 = userRepository.findByEmail(user1.getEmail());
		assertEquals(1, user1.getTasks().size());
		listStatusFalse = taskRepository.findByStatus(false);
		assertEquals(1, listStatusFalse.size());
	}
	
	@Test
	void findByStatusTrueTest() {
		//check initial state (only one task on list)
		List<Task> listStatusTrue = taskRepository.findByStatus(true);
		assertEquals(1, listStatusTrue.size());
		
		//add other task to list
		user2 = userRepository.findByEmail(user2.getEmail());
		assertEquals(1, user2.getTasks().size());
		Task task3 = new Task("test1", "test1", new Date(), true);
		user2.addTask(task3);
		userRepository.save(user2);
		
		//check task list again
		user2 = userRepository.findByEmail(user2.getEmail());
		assertEquals(2, user2.getTasks().size());
		listStatusTrue = taskRepository.findByStatus(true);
		assertEquals(2, listStatusTrue.size());
		
		//delete one task
		user2 = userRepository.findByEmail(user2.getEmail());
		Task task4 = user2.getTasks().get(1);
		user2.removeTask(task4);
		userRepository.save(user2);
		
		//check task list again
		user2 = userRepository.findByEmail(user2.getEmail());
		assertEquals(1, user2.getTasks().size());
		listStatusTrue = taskRepository.findByStatus(true);
		assertEquals(1, listStatusTrue.size());
		
	}
	
}
