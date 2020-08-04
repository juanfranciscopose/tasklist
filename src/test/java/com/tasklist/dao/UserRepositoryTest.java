package com.tasklist.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tasklist.model.User;

@SpringBootTest
class UserRepositoryTest {
    
	@Autowired
	private UserRepository userRepository;
	
	private User user;
	
	@BeforeEach
	public void before() {
		user = new User("Jhon", "Doe", "jhondoe@gmail.com", "asd", 1234);
		userRepository.save(user);
	}
	
	@AfterEach
	public void after() {
		userRepository.delete(userRepository.findByEmail(user.getEmail()).get());
	}
	@Test
	void findByEmailTest() {
		//incorrect email
		assertThrows(NoSuchElementException.class, () -> userRepository.findByEmail("notexist@gmail.com").get());
		//ok
		User userByEmail = userRepository.findByEmail("jhondoe@gmail.com").get();
		assertThat(userByEmail).isNotNull();
	}
	
	@Test
	void existByEmailTest() {
		assertTrue(userRepository.existsByEmail("jhondoe@gmail.com"));
		assertFalse(userRepository.existsByEmail("notexist@gmail.com"));
	}
	
	@Test
	void findByEmailAndPasswordTest() {
		//incorrect password
		User userByEmailAndPassword;
		assertThrows(NoSuchElementException.class, () -> userRepository.findByEmailAndPassword("jhondoe@gmail.com", "123").get());
		//incorrect email
		assertThrows(NoSuchElementException.class, () -> userRepository.findByEmailAndPassword("jhon@gmail.com", "asd").get());
		//ok
		userByEmailAndPassword = userRepository.findByEmailAndPassword("jhondoe@gmail.com", "asd").get();
		assertThat(userByEmailAndPassword).isNotNull();	
	}
	

}
