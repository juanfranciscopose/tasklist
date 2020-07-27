package com.tasklist.service;

import com.tasklist.dto.ToDoRequest;
import com.tasklist.model.ToDo;
import com.tasklist.util.exception.InternalServerErrorException;
import com.tasklist.util.exception.NotFoundException;

public interface ToDoService {
	
	//create "to do" on database
	public void storeToDo(ToDoRequest toDoRequest) throws NotFoundException, InternalServerErrorException;
	//if "to do" exist return it
	public ToDo getToDoById(long id) throws NotFoundException, InternalServerErrorException;
	//remove 'to do' from database
	public void deleteToDo(ToDoRequest toDoRequest) throws InternalServerErrorException, NotFoundException;
	//edit the passed 'to do' as parameter
	public void updateToDo(ToDoRequest toDoRequest) throws InternalServerErrorException;
	//changes status when 'to do' is finished
	public void changeStatus(ToDoRequest toDoRequest) throws InternalServerErrorException;

}
