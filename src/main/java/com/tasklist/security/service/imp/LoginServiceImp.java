package com.tasklist.security.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.tasklist.security.dto.Credential;
import com.tasklist.security.dto.LoginRequest;
import com.tasklist.security.jwt.JwtProvider;
import com.tasklist.security.model.MainUser;
import com.tasklist.security.service.LoginService;

@Service
public class LoginServiceImp implements LoginService{
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Override
	public Credential login(LoginRequest loginRequest) {
		Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(auth);
		String token = jwtProvider.generateToken(auth);
		MainUser mainUser = (MainUser) auth.getPrincipal();
		return new Credential(mainUser.getId(), token, mainUser.getUsername(), mainUser.getAuthorities());
	}

}
