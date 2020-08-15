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
	
	private void idValidator(long id) throws BadRequestException, NotFoundException {
		if(id == 0) {
			throw new BadRequestException("El ID no puede ser cero");
		}
		if(!isTaskExist(id)) {
			throw new NotFoundException("El proyecto con id: "+id+" no existe");	
		}
	}
	
	private void createAndUpdateCommonFieldsValidator(TaskRequest taskRequest) throws UnprocessableEntityException {
		//Required
		if(taskRequest.getTitle() == null || taskRequest.getTitle().isEmpty()) {
			throw new UnprocessableEntityException("El titulo es obligatorio");
		}
		if(taskRequest.getAuthor().getId() == 0) {
			throw new UnprocessableEntityException("El id no puede ser cero");
		}
		
		// min fields
		if(taskRequest.getTitle().length() < 3 ) {
			throw new UnprocessableEntityException("El título es muy corto. Min 3 letras");
		}
				 
		//max fields
		if(taskRequest.getTitle().length() > 100 ) {
			throw new UnprocessableEntityException("El título es muy largo. Max 100 letras");
		}
		if(taskRequest.getDescription() != null && taskRequest.getDescription().length() > 255) {
			throw new UnprocessableEntityException("La descripción es muy larga. Max 255 letras");
		}
	}
	
	@Override
	public void deleteValidator(long id) throws BadRequestException, NotFoundException {
		this.idValidator(id);		
	}

	@Override
	public void createValidator(TaskRequest taskRequest) throws UnprocessableEntityException, NotFoundException {
		this.createAndUpdateCommonFieldsValidator(taskRequest);	
		
		//user exist
		if (! userValidator.isUserExist(taskRequest.getAuthor().getId())) {
			throw new NotFoundException("El autor con id: '"+ taskRequest.getAuthor().getId() +"' no existe");	
		}

	}

	@Override
	public boolean isTaskExist(long id) {
		if(id == 0) {
			return false;
		}
		return taskRepository.existsById(id);
	}

	@Override
	public void updateValidator(TaskRequest taskRequest) throws UnprocessableEntityException, NotFoundException {
		this.createAndUpdateCommonFieldsValidator(taskRequest);
				
		//task exist
		if (! isTaskExist(taskRequest.getId())) {
			throw new NotFoundException("El proyecto con id: '"+ taskRequest.getId() +"' no existe");	
		}
	}

	@Override
	public void getAllTaskToDoValidator(long taskId) throws NotFoundException, BadRequestException {
		this.idValidator(taskId);	
	}

	@Override
	public void getTaskValidator(long taskId) throws BadRequestException, NotFoundException {
		this.idValidator(taskId);	
	}

}
