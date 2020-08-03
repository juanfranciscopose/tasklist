package com.tasklist.util.validator;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tasklist.dao.ToDoRepository;
import com.tasklist.dao.UserRepository;
import com.tasklist.dto.TaskRequest;
import com.tasklist.dto.ToDoRequest;
import com.tasklist.model.Task;
import com.tasklist.model.ToDo;
import com.tasklist.model.User;
import com.tasklist.util.exception.BadRequestException;
import com.tasklist.util.exception.NotFoundException;
import com.tasklist.util.exception.UnprocessableEntityException;

@SpringBootTest
class ToDoValidatorTest {
	
	private static final long HASH_RANDOM = 10000;//used to generate a non-existent id
	
	private Task task;
	private User user;
	private TaskRequest taskRequest = new TaskRequest();
	private ToDoRequest toDoRequest = new ToDoRequest();

	@Autowired
	private ToDoRepository toDoRepository;
	
	@Autowired
	private ToDoValidator toDoValidator;
	
	@Autowired
	private UserRepository userRepository;
	
	@BeforeEach
	void afterEach() {	
		user = new User("Mat", "Dixon", "matdixon@gmail.com", "asd", 67898);
		task = new Task("test1", "test1", new Date(), true);
		user.addTask(task);
		userRepository.save(user);		
	}
	
	@AfterEach
	void beforeEach() {
		user = userRepository.findByEmail("matdixon@gmail.com").get();
		userRepository.delete(user);
	}
	@Test
	void changeStatusValidatorTest() {
		//id = 0
		assertThrows(BadRequestException.class, () -> toDoValidator.changeStatusValidator(0));
		//id incorrect -> non-existent
		long id = toDoRepository.count() + HASH_RANDOM;
		assertThrows(NotFoundException.class, () -> toDoValidator.changeStatusValidator(id));
	}
	
	@Test
	void deleteToDoValidatorTest() {
		//id = 0
		assertThrows(BadRequestException.class, () -> toDoValidator.removeValidator(0));
		//id incorrect -> non-existent
		long id = toDoRepository.count() + HASH_RANDOM;
		assertThrows(NotFoundException.class, () -> toDoValidator.removeValidator(id));
	}
	
	@Test
	void createToDoValidatorTest() {
		user = userRepository.findByEmail("matdixon@gmail.com").get();
		taskRequest.setId(user.getTasks().get(0).getId());
		toDoRequest.setTask(taskRequest);
		toDoRequest.setTimeStamp(new Date());
		//256 characters
		toDoRequest.setDescription("Lorem ipsum dolor sit amet consectetur adipiscing, elit in praesent dictum sagittis, pharetra cubilia felis risus nunc. Mattis mollis varius augue urna luctus sollicitudin litora donec, ac per justo sociis ligula a blandit, accumsan metus senectus sceler..");
		assertThrows(UnprocessableEntityException.class, () -> toDoValidator.createValidator(toDoRequest));
		//255 characters
		toDoRequest.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque non nulla porttitor nulla fringilla convallis sed vitae ligula. Proin congue augue non sem consectetur tristique. Vestibulum viverra turpis et lorem lacinia, id congue justo erat curae.");
		assertDoesNotThrow(() -> toDoValidator.createValidator(toDoRequest));
		//2 characters
		toDoRequest.setDescription("as");
		assertThrows(UnprocessableEntityException.class, () -> toDoValidator.createValidator(toDoRequest));
		//3 characters
		toDoRequest.setDescription("asd");
		assertDoesNotThrow(() -> toDoValidator.createValidator(toDoRequest));
	}
	
	@Test
	void updateToDoValidatorTest() {
		//config initial state
		user = userRepository.findByEmail("matdixon@gmail.com").get();
		ToDo toDo = new ToDo("test", new Date());
		user.getTasks().get(0).addToDo(toDo);
		userRepository.save(user);
		
		user = userRepository.findByEmail("matdixon@gmail.com").get();
		taskRequest.setId(user.getTasks().get(0).getId());
		toDoRequest.setTask(taskRequest);
		toDoRequest.setTimeStamp(user.getTasks().get(0).getTimeStamp());
		toDoRequest.setDescription(user.getTasks().get(0).getDescription());
		
		//id = 0
		toDoRequest.setId(0);
		assertThrows(BadRequestException.class, () -> toDoValidator.editValidator(toDoRequest));
		//id incorrect -> non-existent
		long id = toDoRepository.count() + HASH_RANDOM;
		toDoRequest.setId(id);
		assertThrows(NotFoundException.class, () -> toDoValidator.editValidator(toDoRequest));
		
		//256 characters
		user = userRepository.findByEmail("matdixon@gmail.com").get();
		ToDo toDo1 = user.getTasks().get(0).getList().get(0);
		toDoRequest.setId(toDo1.getId());//correct id
		toDoRequest.setDescription("Lorem ipsum dolor sit amet consectetur adipiscing, elit in praesent dictum sagittis, pharetra cubilia felis risus nunc. Mattis mollis varius augue urna luctus sollicitudin litora donec, ac per justo sociis ligula a blandit, accumsan metus senectus sceler..");
		assertThrows(UnprocessableEntityException.class, () -> toDoValidator.editValidator(toDoRequest));
		//255 characters
		toDoRequest.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque non nulla porttitor nulla fringilla convallis sed vitae ligula. Proin congue augue non sem consectetur tristique. Vestibulum viverra turpis et lorem lacinia, id congue justo erat curae.");
		assertDoesNotThrow(() -> toDoValidator.editValidator(toDoRequest));//description correct
		//2 characters
		toDoRequest.setDescription("as");
		assertThrows(UnprocessableEntityException.class, () -> toDoValidator.editValidator(toDoRequest));
		//3 characters
		toDoRequest.setDescription("asd");//description correct
		assertDoesNotThrow(() -> toDoValidator.editValidator(toDoRequest));	
	}
}
