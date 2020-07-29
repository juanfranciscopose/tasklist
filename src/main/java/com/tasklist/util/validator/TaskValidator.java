package com.tasklist.util.validator;

import com.tasklist.dto.TaskRequest;
import com.tasklist.util.exception.BadRequestException;
import com.tasklist.util.exception.NotFoundException;
import com.tasklist.util.exception.UnprocessableEntityException;

public interface TaskValidator {
	public void deleteValidator(long id) throws BadRequestException, NotFoundException;
	public void createValidator(TaskRequest task) throws UnprocessableEntityException, NotFoundException;
	public boolean isTaskExist(long id);
	public void updateValidator(TaskRequest task) throws UnprocessableEntityException, NotFoundException;
	public void getAllTaskToDoValidator(long taskId) throws NotFoundException, UnprocessableEntityException;
}
