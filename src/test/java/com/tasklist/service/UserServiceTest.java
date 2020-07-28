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
				
		//--password
		userRequest.setSurname("Doe");
		//9 characters
		userRequest.setPassword("123456789");
		assertThrows(InternalServerErrorException.class, () -> userService.storeUser(userRequest));

		//--email
		userRequest.setPassword("asd");
		//101 characters
		userRequest.setEmail("Lorem ipsum dolor sit amet consectetur adipiscing elit luctus at euismod tristique, metus nisi urna..");
		assertThrows(InternalServerErrorException.class, () -> userService.storeUser(userRequest));
		
		userRequest.setEmail("jhondoe@gmail.com");
		
		//right way
		//assertDoesNotThrow(() -> userService.storeUser(userRequest));
		
	}

}
