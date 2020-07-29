package com.tasklist.util.validator;

import com.tasklist.dto.ToDoRequest;
import com.tasklist.util.exception.BadRequestException;
import com.tasklist.util.exception.NotFoundException;
import com.tasklist.util.exception.UnprocessableEntityException;

public interface ToDoValidator {
	public void removeValidator(long id) throws BadRequestException, NotFoundException;
	public void createValidator(ToDoRequest toDo) throws UnprocessableEntityException, NotFoundException;
	public boolean isToDoExist(long id);
	public void editValidator(ToDoRequest toDo) throws UnprocessableEntityException, NotFoundException, BadRequestException;
	public void changeStatusValidator(ToDoRequest toDo) throws BadRequestException, NotFoundException;
}
