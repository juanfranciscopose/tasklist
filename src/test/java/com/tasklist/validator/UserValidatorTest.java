package com.tasklist.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tasklist.dao.UserRepository;
import com.tasklist.dto.UserRequest;
import com.tasklist.model.User;
import com.tasklist.util.exception.BadRequestException;
import com.tasklist.util.exception.NotFoundException;
import com.tasklist.util.exception.UnprocessableEntityException;
import com.tasklist.util.validator.UserValidator;

@SpringBootTest
class UserValidatorTest {
	
	private static final long HASH_RANDOM = 10000;//used to generate a non-existent id

	
	private UserRequest userRequest;
	
	@Autowired
	private UserValidator userValidator;
	
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
		User user = userRepository.findByEmail("matdixon@gmail.com");
		userRepository.delete(user);
	}
	
	@Test
	void deleteUserTest() {
		//id = 0
		assertThrows(BadRequestException.class, () -> userValidator.removeValidator(0));
		//id incorrect -> non-existent
		long id = userRepository.count() + HASH_RANDOM;
		assertThrows(NotFoundException.class, () -> userValidator.removeValidator(id));
		
		//right way
		User user = new User("Dan", "Carter", "dancarter@gmail.com", "asd", 67898);
		userRepository.save(user);
		long removeId = userRepository.findByEmail("dancarter@gmail.com").getId();
		assertDoesNotThrow(()-> userValidator.removeValidator(removeId));
		userRepository.delete(user);
	}
	
	@Test
	void createUserValidatorTest() {
		//--MIN FIELDS
		//name
		//2 characters
		userRequest.setName("as");
		assertThrows(UnprocessableEntityException.class, () -> userValidator.createValidator(userRequest));
		//3 characters
		userRequest.setName("asd");
		assertDoesNotThrow(() -> userValidator.createValidator(userRequest));
		
		//surname
		userRequest.setName("Jhon");
		//2 characters
		userRequest.setSurname("as");
		assertThrows(UnprocessableEntityException.class, () -> userValidator.createValidator(userRequest));
		//3 characters
		userRequest.setSurname("asd");
		assertDoesNotThrow(() -> userValidator.createValidator(userRequest));
			
		//password
		userRequest.setSurname("Doe");
		//2 characters
		userRequest.setPassword("as");
		assertThrows(UnprocessableEntityException.class, () -> userValidator.createValidator(userRequest));
		//3 characters
		userRequest.setPassword("asd");
		assertDoesNotThrow(() -> userValidator.createValidator(userRequest));
		
		//email
		userRequest.setPassword("asd");
		//2 characters
		userRequest.setEmail("as");
		assertThrows(UnprocessableEntityException.class, () -> userValidator.createValidator(userRequest));
		//3 characters and '@'
		userRequest.setEmail("as@");
		assertDoesNotThrow(() -> userValidator.createValidator(userRequest));
		userRequest.setEmail("jhondoe@gmail.com");
		
		//REQUIRED FIELDS
		//empty string
		userRequest.setName("");
		assertThrows(UnprocessableEntityException.class, () -> userValidator.createValidator(userRequest));
	
		userRequest.setName("Jhon");
		userRequest.setSurname("");
		assertThrows(UnprocessableEntityException.class, () -> userValidator.createValidator(userRequest));
	
		userRequest.setSurname("Doe");
		userRequest.setPassword("");
		assertThrows(UnprocessableEntityException.class, () -> userValidator.createValidator(userRequest));
	
		userRequest.setPassword("asd");
		userRequest.setEmail("");
		assertThrows(UnprocessableEntityException.class, () -> userValidator.createValidator(userRequest));
		userRequest.setEmail("jhondoe@gmail.com");
		
		//REQUIRED FIELDS
		//null string
		userRequest.setName(null);
		assertThrows(UnprocessableEntityException.class, () -> userValidator.createValidator(userRequest));
	
		userRequest.setName("Jhon");
		userRequest.setSurname(null);
		assertThrows(UnprocessableEntityException.class, () -> userValidator.createValidator(userRequest));
	
		userRequest.setSurname("Doe");
		userRequest.setPassword(null);
		assertThrows(UnprocessableEntityException.class, () -> userValidator.createValidator(userRequest));
	
		userRequest.setPassword("asd");
		userRequest.setEmail(null);
		assertThrows(UnprocessableEntityException.class, () -> userValidator.createValidator(userRequest));
		userRequest.setEmail("jhondoe@gmail.com");
	
		//--- MAX FIELDS --
		//--name
		//151 characters
		userRequest.setName("Lorem ipsum dolor sit amet consectetur adipiscing elit lacus iaculis, pretium tempus volutpat nec dignissim et hendrerit praesent. Metus ultricies vo..");
		assertThrows(UnprocessableEntityException.class, () -> userValidator.createValidator(userRequest));
		//150 characters
		userRequest.setName("Lorem ipsum dolor sit amet consectetur adipiscing elit lacus iaculis, pretium tempus volutpat nec dignissim et hendrerit praesent. Metus ultricies vo.");
		assertDoesNotThrow(() -> userValidator.createValidator(userRequest));
		
		//--surname
		userRequest.setName("Jhon");
		//201 characters
		userRequest.setSurname("Lorem ipsum dolor sit amet consectetur adipiscing elit luctus at euismod tristique, metus nisi urna.Lorem ipsum dolor sit amet consectetur adipiscing elit luctus at euismod tristique, metus nisi urna..");
		assertThrows(UnprocessableEntityException.class, () -> userValidator.createValidator(userRequest));
		//200 characters
		userRequest.setSurname("Lorem ipsum dolor sit amet consectetur adipiscing elit luctus at euismod tristique, metus nisi urna.Lorem ipsum dolor sit amet consectetur adipiscing elit luctus at euismod tristique, metus nisi urna.");
		assertDoesNotThrow(() -> userValidator.createValidator(userRequest));
		
		//--password
		userRequest.setSurname("Doe");
		//9 characters
		userRequest.setPassword("123456789");
		assertThrows(UnprocessableEntityException.class, () -> userValidator.createValidator(userRequest));
		//8 characters
		userRequest.setPassword("12345678");
		assertDoesNotThrow(()-> userValidator.createValidator(userRequest));

		//--email
		userRequest.setPassword("asd");
		//101 characters
		userRequest.setEmail("Lorem ipsum dolor sit amet consectetur adipiscing elit luctus at euismod tristique, metus nisi urna..");
		assertThrows(UnprocessableEntityException.class, () -> userValidator.createValidator(userRequest));
		//100 characters and contains "@"
		userRequest.setEmail("Lorem ipsum dolor sit amet@consectetur adipiscing elit luctus at euismod tristique, metus nisi urna.");
		assertDoesNotThrow(() -> userValidator.createValidator(userRequest));
		
		//--EMAIL IS CORRECT? -> if contains '@'
		//without '@'
		userRequest.setEmail("jhondoegmail.com");
		assertThrows(UnprocessableEntityException.class, () -> userValidator.createValidator(userRequest));
		//with '@'
		userRequest.setEmail("jhondoe@gmail.com");
		assertDoesNotThrow(() -> userValidator.createValidator(userRequest));
		
		//--EMAIL EXIST?
		//yes
		userRequest.setEmail("matdixon@gmail.com");
		assertThrows(UnprocessableEntityException.class, () -> userValidator.createValidator(userRequest));
		//no
		userRequest.setEmail("jhondoe@gmail.com");
		assertDoesNotThrow(() -> userValidator.createValidator(userRequest));
	}

}
