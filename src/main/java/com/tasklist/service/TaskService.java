package com.tasklist.service;

import java.util.List;

import com.tasklist.dto.TaskRequest;
import com.tasklist.dto.ToDoRequest;
import com.tasklist.model.Task;
import com.tasklist.util.exception.InternalServerErrorException;
import com.tasklist.util.exception.NotFoundException;

public interface TaskService {
	//returns all public tasks
	public List<TaskRequest> getAllPublicTask() throws InternalServerErrorException;
	//create task in database
	public void storeTask(TaskRequest taskRequest) throws NotFoundException, InternalServerErrorException;
	//if task exist return it
	public Task getTaskById(long id) throws NotFoundException, InternalServerErrorException;
	//Remove task from database
	public void deleteTask(TaskRequest taskRequest) throws InternalServerErrorException, NotFoundException;
	//edit the passed task as parameter
	public void updateTask(TaskRequest taskRequest) throws InternalServerErrorException;
	//return list of 'to do' object
	public List<ToDoRequest> getAllToDo(long taskId) throws InternalServerErrorException;
}
