package com.tasklist.service.imp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tasklist.dao.UserRepository;
import com.tasklist.dto.TaskRequest;
//import com.tasklist.dto.ToDoRequest;
import com.tasklist.dto.UserRequest;
import com.tasklist.model.Task;
//import com.tasklist.model.ToDo;
import com.tasklist.model.User;
import com.tasklist.security.enums.RolName;
import com.tasklist.security.model.Rol;
import com.tasklist.security.service.RolService;
import com.tasklist.service.UserService;
import com.tasklist.util.exception.InternalServerErrorException;
import com.tasklist.util.exception.NotFoundException;

@Service
public class UserServiceImp implements UserService{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RolService rolService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void storeUser(UserRequest userRequest) throws InternalServerErrorException {
		try {
			//generate user(data base)
			User user = new User(userRequest.getName(), userRequest.getSurname(), userRequest.getEmail(), 
					userRequest.getPassword(), userRequest.getTelephone());
			//encoder pass
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			//set rols
			Set<Rol> rols = new HashSet<>();
			rols.add(rolService.getRolByRolName(RolName.ROL_USER));
			if (userRequest.getRols() != null && userRequest.getRols().contains("admin")) {
				rols.add(rolService.getRolByRolName(RolName.ROL_ADMIN));
			}
			user.setRols(rols);
            
			userRepository.save(user);
       } catch (Exception e) {
    	   throw new InternalServerErrorException(e.toString());
       }	
	}

	@Override
	public User getUserById(long id) throws NotFoundException, InternalServerErrorException {
		try {
			User user = userRepository.findDistinctById(id).get();
	        return user;	
		} catch (NoSuchElementException  e) {
			throw new NotFoundException("user with id "+id+" not exist");
		}catch (Exception e) {
			throw new InternalServerErrorException(e.toString());
		}
	}

	@Override
	//refactor
	public List<TaskRequest> getAllUserTask(long id) throws InternalServerErrorException {
		List<TaskRequest> taskListResult = new ArrayList<>();
		try {
			List<Task> taskList = getUserById(id).getTasks();
			
			for (Task task : taskList) {
				
				/*	List<ToDoRequest> toDoListResult = new ArrayList<>();
				for (ToDo toDo : task.getList()) {00
					ToDoRequest toDoRequest = new ToDoRequest(toDo.getId(), toDo.getDescription(), toDo.getTimeStamp(), null, toDo.isStatus());
					toDoListResult.add(toDoRequest);
				}*/
				
				TaskRequest taskRequest = new TaskRequest(task.getId(), task.getTitle(), task.getDescription(), task.getTimeStamp(), 
									 task.isStatus(), null, null);
				taskListResult.add(taskRequest);
			}
			Collections.sort(taskListResult);
			return taskListResult;
		}catch (Exception e) {
			throw new InternalServerErrorException(e.toString());
		}
	}

	@Override
	public void deleteUser(long id) throws InternalServerErrorException {
		try {
			User user = getUserById(id);
			userRepository.delete(user);
		} catch (Exception e) {
			throw new InternalServerErrorException(e.toString());
		}
	}

	
}
