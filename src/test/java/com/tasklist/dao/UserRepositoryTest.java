package com.tasklist.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
		userRepository.delete(user);
	}
	@Test
	void findByEmailTest() {
		//incorrect email
		User userByEmail = userRepository.findByEmail("notexist@gmail.com");
		assertThat(userByEmail).isNull();
		//ok
		userByEmail = userRepository.findByEmail("jhondoe@gmail.com");
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
		User userByEmailAndPassword = userRepository.findByEmailAndPassword("jhondoe@gmail.com", "123");
		assertThat(userByEmailAndPassword).isNull();
		//incorrect email
		userByEmailAndPassword = userRepository.findByEmailAndPassword("jhon@gmail.com", "asd");
		assertThat(userByEmailAndPassword).isNull();
		//ok
		userByEmailAndPassword = userRepository.findByEmailAndPassword("jhondoe@gmail.com", "asd");
		assertThat(userByEmailAndPassword).isNotNull();	
	}
	

}
