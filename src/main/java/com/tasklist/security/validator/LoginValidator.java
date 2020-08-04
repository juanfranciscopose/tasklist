package com.tasklist.security.validator;

import com.tasklist.security.dto.LoginRequest;
import com.tasklist.util.exception.UnprocessableEntityException;

public interface LoginValidator {
	public void loginValidator(LoginRequest loginRequest) throws UnprocessableEntityException;
}
