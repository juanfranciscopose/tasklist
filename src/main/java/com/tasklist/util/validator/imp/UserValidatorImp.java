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

	@Override
	public void getAllUserTaskValidator(long id) throws BadRequestException, NotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeValidator(long id) throws BadRequestException, NotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createValidator(UserRequest userRequest) throws UnprocessableEntityException {
		//required
		if(userRequest.getEmail() == null || userRequest.getEmail().isEmpty()) {
			throw new UnprocessableEntityException("email is required");
		}
		if(userRequest.getName() == null || userRequest.getName().isEmpty()) {
			throw new UnprocessableEntityException("name is required");
		}
		if(userRequest.getSurname() == null || userRequest.getSurname().isEmpty()) {
			throw new UnprocessableEntityException("surname is required");
		}
		if(userRequest.getPassword() == null || userRequest.getPassword().isEmpty()) {
			throw new UnprocessableEntityException("password is required");
		}
				
		//min fields
		if(userRequest.getName().length() < 3 ) {
			throw new UnprocessableEntityException("the name is very short. Min 3 characters");
		}
		if(userRequest.getSurname().length() < 3 ) {
			throw new UnprocessableEntityException("the surname is very short. Min 3 characters");
		}
		if(userRequest.getPassword().length() < 3 ) {
			throw new UnprocessableEntityException("the password is very short. Min 3 characters");
		}
		if(userRequest.getEmail().length() < 3 ) {
			throw new UnprocessableEntityException("the email is very short");
		}
		
		//max fields
		if(userRequest.getName().length() > 150 ) {
			throw new UnprocessableEntityException("the name is long. Max 150 characters");
		}
		if(userRequest.getSurname().length() > 200 ) {
			throw new UnprocessableEntityException("the surname is very long. Max 200 characters");
		}
		if(userRequest.getPassword().length() > 8 ) {
			throw new UnprocessableEntityException("the password is very long. Max 8 characters");
		}
		if(userRequest.getEmail().length() > 100 ) {
			throw new UnprocessableEntityException("the email is very long. Max 100 characters");
		}
		
		//email is correct?
		if(!userRequest.getEmail().contains("@")) {
			throw new UnprocessableEntityException("the email is wrong");
		}
				
		//unique email
		if (this.isUserExist(userRequest.getEmail())) {
			throw new UnprocessableEntityException("this email is being used");
		}
	}

	@Override
	public boolean isUserExist(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public boolean isUserExist(long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
