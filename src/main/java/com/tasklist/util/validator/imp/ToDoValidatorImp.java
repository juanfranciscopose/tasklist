package com.tasklist.util.validator.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tasklist.dao.ToDoRepository;
import com.tasklist.dto.ToDoRequest;
import com.tasklist.util.exception.BadRequestException;
import com.tasklist.util.exception.NotFoundException;
import com.tasklist.util.exception.UnprocessableEntityException;
import com.tasklist.util.validator.TaskValidator;
import com.tasklist.util.validator.ToDoValidator;

@Service
public class ToDoValidatorImp implements ToDoValidator{
	@Autowired
	private ToDoRepository toDoRepository;
	@Autowired
	private TaskValidator taskValidator;

	private void idValidator(long id) throws BadRequestException, NotFoundException {
		if(id == 0) {
			throw new BadRequestException("id can't be 0");
		}
		if(!isToDoExist(id)) {
			throw new NotFoundException("'to do' with id "+id+" not exist");	
		}
	}
	
	@Override
	public void removeValidator(long id) throws BadRequestException, NotFoundException {
		this.idValidator(id);		
	}
	
	private void createAndUpdateCommonFieldsValidator(ToDoRequest toDoRequest) throws UnprocessableEntityException {
		//Required
		if(toDoRequest.getDescription() == null || toDoRequest.getDescription().isEmpty()) {
			throw new UnprocessableEntityException("description is required");
		}
		//min field
		if(toDoRequest.getDescription().length() < 3 ) {
			throw new UnprocessableEntityException("the description is very short. Min 3 characters");
		}
		//max field
		if(toDoRequest.getDescription().length() > 255 ) {
			throw new UnprocessableEntityException("the description is very long. Max 255 characters");
		}	
	}

	@Override
	public void createValidator(ToDoRequest toDoRequest) throws UnprocessableEntityException, NotFoundException {
		this.createAndUpdateCommonFieldsValidator(toDoRequest);
		//task exist
		if (! taskValidator.isTaskExist(toDoRequest.getTask().getId())) {
			throw new NotFoundException("task with id '"+ toDoRequest.getTask().getId() +"' not exist");	
		}	
	}

	@Override
	public boolean isToDoExist(long id) {
		if(id == 0) {
			return false;
		}
		return toDoRepository.existsById(id);
	}

	@Override
	public void editValidator(ToDoRequest toDoRequest)
			throws UnprocessableEntityException, NotFoundException, BadRequestException {
		this.createAndUpdateCommonFieldsValidator(toDoRequest);
		//validate id field
		this.idValidator(toDoRequest.getId());
	}

	@Override
	public void changeStatusValidator(ToDoRequest toDoRequest) throws BadRequestException, NotFoundException {
		//validate id field
		this.idValidator(toDoRequest.getId());		
	}
	
}
