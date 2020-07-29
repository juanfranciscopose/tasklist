package com.tasklist.util.validator.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tasklist.dao.TaskRepository;
import com.tasklist.dto.TaskRequest;
import com.tasklist.util.exception.BadRequestException;
import com.tasklist.util.exception.NotFoundException;
import com.tasklist.util.exception.UnprocessableEntityException;
import com.tasklist.util.validator.TaskValidator;
import com.tasklist.util.validator.UserValidator;

@Service
public class TaskValidatorImp implements TaskValidator{
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Override
	public void deleteValidator(long id) throws BadRequestException, NotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createValidator(TaskRequest taskRequest) throws UnprocessableEntityException, NotFoundException {
		//Required
		if(taskRequest.getTitle() == null || taskRequest.getTitle().isEmpty()) {
			throw new UnprocessableEntityException("title is required");
		}
		if(taskRequest.getAuthor().getId() == 0) {
			throw new UnprocessableEntityException("id can't be 0");
		}
		
		// min fields
		if(taskRequest.getTitle().length() < 3 ) {
			throw new UnprocessableEntityException("the title is very short. Min 3 characters");
		}
		 
		//max fields
		if(taskRequest.getTitle().length() > 100 ) {
			throw new UnprocessableEntityException("the title is very long. Max 100 characters");
		}
		if(taskRequest.getDescription().length() > 255) {
			throw new UnprocessableEntityException("the description is very long. Max 255 characters");
		}
		
		//user exist
		if (! userValidator.isUserExist(taskRequest.getAuthor().getId())) {
			throw new NotFoundException("user (author) with id '"+ taskRequest.getAuthor().getId() +"' not exist");	
		}

	}

	@Override
	public boolean isTaskExist(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateValidator(TaskRequest task) throws UnprocessableEntityException, NotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getAllTaskToDoValidator(long taskId) throws NotFoundException, UnprocessableEntityException {
		// TODO Auto-generated method stub
		
	}

}
