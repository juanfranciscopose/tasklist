package com.tasklist.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tasklist.dao.UserRepository;
import com.tasklist.dto.UserRequest;
import com.tasklist.model.User;
import com.tasklist.util.exception.InternalServerErrorException;

@SpringBootTest
class UserServiceTest {
	private static final long HASH_RANDOM = 10000;//used to generate a non-existent id

	@Autowired
	private UserService userService;
	 
	private UserRequest userRequest;
	
	@Autowired
	private UserRepository userRepository;
	
	@BeforeEach
	void afterEach() {
		userRequest = new UserRequest(0, "Jhon", "Doe", "jhondoe@gmail.com", "asd", 12345);
		User user = new User("Mat", "Dixon", "matdixon@gmail.com", "asd", 67898);
		userRepository.save(user);
	}
	
	@AfterEach
	void beforeEach() {
		User user = userRepository.findByEmail("matdixon@gmail.com").get();
		userRepository.delete(user);
	}
	@Test
	void storeAndDeleteUserTest() {
		//---- EXCEPTION TESTING ----
		
		//--name
		//151 characters
		userRequest.setName("Lorem ipsum dolor sit amet consectetur adipiscing elit lacus iaculis, pretium tempus volutpat nec dignissim et hendrerit praesent. Metus ultricies vo..");
		assertThrows(InternalServerErrorException.class, () -> userService.storeUser(userRequest));
				
		//--surname
		userRequest.setName("Jhon");
		//201 characters
		userRequest.setSurname("Lorem ipsum dolor sit amet consectetur adipiscing elit luctus at euismod tristique, metus nisi urna.Lorem ipsum dolor sit amet consectetur adipiscing elit luctus at euismod tristique, metus nisi urna..");
		assertThrows(InternalServerErrorException.class, () -> userService.storeUser(userRequest));
				
		//--email
		userRequest.setSurname("Doe");
		//101 characters
		userRequest.setEmail("Lorem ipsum dolor sit amet consectetur adipiscing elit luctus at euismod tristique, metus nisi urna..");
		assertThrows(InternalServerErrorException.class, () -> userService.storeUser(userRequest));
		
		//not unique email
		userRequest.setEmail("matdixon@gmail.com");
		assertThrows(InternalServerErrorException.class, () -> userService.storeUser(userRequest));
		
		//---RIGHT WAY
		//create
		userRequest.setEmail("jhondoe@gmail.com");
		assertDoesNotThrow(() -> userService.storeUser(userRequest));
		
		//delete
		User user = userRepository.findByEmail("jhondoe@gmail.com").get();
		assertDoesNotThrow(() -> userService.deleteUser(user.getId()));
	}

	@Test
	void getAllUserTaskTest() {
		//id = 0
		assertThrows(InternalServerErrorException.class, () -> userService.getAllUserTask(0));
		//id incorrect -> non-existent
		long id = userRepository.count() + HASH_RANDOM;
		assertThrows(InternalServerErrorException.class, () -> userService.getAllUserTask(id));
	}

}
