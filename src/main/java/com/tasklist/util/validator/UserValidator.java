package com.tasklist.util.validator;

import com.tasklist.dto.UserRequest;
import com.tasklist.util.exception.BadRequestException;
import com.tasklist.util.exception.NotFoundException;
import com.tasklist.util.exception.UnprocessableEntityException;

public interface UserValidator {
		public void getAllUserTaskValidator(long id) throws BadRequestException, NotFoundException;
		public void removeValidator(long id) throws BadRequestException, NotFoundException;
		public void createValidator(UserRequest user) throws UnprocessableEntityException;
		public boolean isUserExist(String email);
		public boolean isUserExist(long id);
}
