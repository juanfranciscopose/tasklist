package com.tasklist.security.service;

import com.tasklist.security.dto.Credential;
import com.tasklist.security.dto.LoginRequest;

public interface LoginService {
	public Credential login(LoginRequest loginRequest);
}
