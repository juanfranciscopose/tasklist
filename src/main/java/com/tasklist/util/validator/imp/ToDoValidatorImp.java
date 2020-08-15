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
			throw new BadRequestException("El ID no puede ser cero");
		}
		if(!isToDoExist(id)) {
			throw new NotFoundException("El item con id: "+id+" no existe");	
		}
	}
	
	@Override
	public void removeValidator(long id) throws BadRequestException, NotFoundException {
		this.idValidator(id);		
	}
	
	private void createAndUpdateCommonFieldsValidator(ToDoRequest toDoRequest) throws UnprocessableEntityException {
		//Required
		if(toDoRequest.getDescription() == null || toDoRequest.getDescription().isEmpty()) {
			throw new UnprocessableEntityException("La descripción es obligatoria");
		}
		//min field
		if(toDoRequest.getDescription().length() < 3 ) {
			throw new UnprocessableEntityException("La descripción es muy corta. Min 3 letras");
		}
		//max field
		if(toDoRequest.getDescription().length() > 255 ) {
			throw new UnprocessableEntityException("La descripción es muy larga. Max 255 letras");
		}	
	}

	@Override
	public void createValidator(ToDoRequest toDoRequest) throws UnprocessableEntityException, NotFoundException {
		this.createAndUpdateCommonFieldsValidator(toDoRequest);
		//task exist
		if (! taskValidator.isTaskExist(toDoRequest.getTask().getId())) {
			throw new NotFoundException("El proyecto con id: '"+ toDoRequest.getTask().getId() +"' no existe");	
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
	public void changeStatusValidator(long id) throws BadRequestException, NotFoundException {
		//validate id field
		this.idValidator(id);		
	}
	
}
