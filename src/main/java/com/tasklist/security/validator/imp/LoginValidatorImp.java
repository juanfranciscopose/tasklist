package com.tasklist.security.validator.imp;

import org.springframework.stereotype.Service;

import com.tasklist.security.dto.LoginRequest;
import com.tasklist.security.validator.LoginValidator;
import com.tasklist.util.exception.UnprocessableEntityException;

@Service
public class LoginValidatorImp  implements LoginValidator{

	@Override
	public void loginValidator(LoginRequest loginRequest) throws UnprocessableEntityException {
		//required
		if(loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty() || loginRequest.getUsername().equals(" ")) {
			throw new UnprocessableEntityException("El nombre de usuario es obligatorio");
		}
		if(loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty() || loginRequest.getPassword().equals(" ")) {
			throw new UnprocessableEntityException("La contraseña es obligatoria");
		}
		//min field
		if(loginRequest.getUsername().length() < 3 ) {
			throw new UnprocessableEntityException("El nombre de usuario es muy corto");
		}
		//username correct
		if(!loginRequest.getUsername().contains("@")) {
			throw new UnprocessableEntityException("El email es incorrecto");
		}
		
	}

}
