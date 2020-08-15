package com.tasklist.util.validator.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tasklist.dao.UserRepository;
import com.tasklist.dto.UserRequest;
import com.tasklist.util.exception.BadRequestException;
import com.tasklist.util.exception.NotFoundException;
import com.tasklist.util.exception.UnprocessableEntityException;
import com.tasklist.util.validator.UserValidator;

@Service
public class UserValidatorImp implements UserValidator{
	
	@Autowired
	private UserRepository userRepository;

	private void idValidator(long id) throws BadRequestException, NotFoundException {
		if(id == 0) {
			throw new BadRequestException("El ID no puede ser cero");
		}
		if(!isUserExist(id)) {
			throw new NotFoundException("El usuario con id: "+id+" no existe");	
		}
	}
	
	@Override
	public boolean isUserExist(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public boolean isUserExist(long id) {
		if(id == 0) {
			return false;
		}
		return userRepository.existsById(id);
	}
	
	@Override
	public void getAllUserTaskValidator(long id) throws BadRequestException, NotFoundException {
		this.idValidator(id);
	}

	@Override
	public void removeValidator(long id) throws BadRequestException, NotFoundException {
		this.idValidator(id);		
	}

	@Override
	public void createValidator(UserRequest userRequest) throws UnprocessableEntityException {
		//required
		if(userRequest.getEmail() == null || userRequest.getEmail().isEmpty()) {
			throw new UnprocessableEntityException("El email es obligatorio");
		}
		if(userRequest.getName() == null || userRequest.getName().isEmpty()) {
			throw new UnprocessableEntityException("El nombre es obligatorio");
		}
		if(userRequest.getSurname() == null || userRequest.getSurname().isEmpty()) {
			throw new UnprocessableEntityException("El apellido es obligatorio");
		}
		if(userRequest.getPassword() == null || userRequest.getPassword().isEmpty()) {
			throw new UnprocessableEntityException("La contraseña es obligatoria");
		}
				
		//min fields
		if(userRequest.getName().length() < 3 ) {
			throw new UnprocessableEntityException("El nombre es muy corto. Min 3 letras");
		}
		if(userRequest.getSurname().length() < 3 ) {
			throw new UnprocessableEntityException("El apellido es muy corto. Min 3 letras");
		}
		if(userRequest.getPassword().length() < 3 ) {
			throw new UnprocessableEntityException("La contraseña es muy corta. Min 3 letras");
		}
		if(userRequest.getEmail().length() < 3 ) {
			throw new UnprocessableEntityException("El email es muy corto. Min 3 letras");
		}
		
		//max fields
		if(userRequest.getName().length() > 150 ) {
			throw new UnprocessableEntityException("El nombre es muy largo. Max 150 letras");
		}
		if(userRequest.getSurname().length() > 200 ) {
			throw new UnprocessableEntityException("El apellido es muy largo. Max 200 letras");
		}
		if(userRequest.getPassword().length() > 8 ) {
			throw new UnprocessableEntityException("La contraseña es muy larga. Max 8 letras");
		}
		if(userRequest.getEmail().length() > 100 ) {
			throw new UnprocessableEntityException("El email es muy largo. Max 100 letras");
		}
		
		//email is correct?
		if(!userRequest.getEmail().contains("@")) {
			throw new UnprocessableEntityException("El email esta mal");
		}
				
		//unique email
		if (this.isUserExist(userRequest.getEmail())) {
			throw new UnprocessableEntityException("Este email ya esta siendo usado por otro usuario");
		}
	}

}
