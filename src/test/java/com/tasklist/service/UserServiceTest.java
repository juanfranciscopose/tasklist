package com.tasklist.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tasklist.dto.UserRequest;
import com.tasklist.util.exception.InternalServerErrorException;

@SpringBootTest
class UserServiceTest {

	@Autowired
	private UserService userService;
	 
	private UserRequest userRequest = new UserRequest(0, "Jhon", "Doe", "jhondoe@gmail.com", "asd", 12345);
	
	@Test
	void storeUserTest() {
		//exceptions testing
		userRequest.setEmail("this email must exceed 100 characters to generate the error. This email must exceed 100 characters to generate the error.");
		assertThrows(InternalServerErrorException.class, () -> userService.storeUser(userRequest));
		
		userRequest.setEmail("jhondoe@gmail.com");
		userRequest.setName("Name must exceed 150 characters to generate the error. Name must exceed 150 characters to generate the error. Name must exceed 150 characters to generate the error. Name must exceed 150 characters to generate the error.");
		assertThrows(InternalServerErrorException.class, () -> userService.storeUser(userRequest));
		
		userRequest.setName("Jhon");
		userRequest.setSurname("Surname must exceed 200 characters to generate the error. Surname must exceed 200 characters to generate the error. Surname must exceed 200 characters to generate the error. Surname must exceed 200 characters to generate the error. Surname must exceed 200 characters to generate the error.");
		assertThrows(InternalServerErrorException.class, () -> userService.storeUser(userRequest));
		
		userRequest.setSurname("Doe");
		userRequest.setPassword("123456789");
		assertThrows(InternalServerErrorException.class, () -> userService.storeUser(userRequest));
		
		//right way
		userRequest.setPassword("asd");
		assertDoesNotThrow(() -> userService.storeUser(userRequest));
		
	}

}
