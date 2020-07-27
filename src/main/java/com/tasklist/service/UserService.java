package com.tasklist.service;

import java.util.List;

import com.tasklist.dto.TaskRequest;
import com.tasklist.dto.UserRequest;
import com.tasklist.model.User;
import com.tasklist.util.exception.InternalServerErrorException;
import com.tasklist.util.exception.NotFoundException;

public interface UserService {
	//create new user
	public void storeUser(UserRequest userRequest) throws InternalServerErrorException;
	//check that the user exists and returns it
	public User getUserById(long id) throws NotFoundException, InternalServerErrorException;
	//return a list with tasks of any given user. Can return an empty list
	public List<TaskRequest> getAllUserTask(long id) throws InternalServerErrorException;
	//remove user
	public void deleteUser(long id) throws InternalServerErrorException;
}
