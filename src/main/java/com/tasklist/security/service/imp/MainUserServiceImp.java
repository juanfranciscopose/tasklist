package com.tasklist.security.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tasklist.dao.UserRepository;
import com.tasklist.model.User;
import com.tasklist.security.model.MainUser;

@Service
public class MainUserServiceImp implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	//converts the user class into a main user class (necessary for spring security)
	//it takes a user from the database and makes it main user
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username).get();
		return MainUser.buildMainUserFromUser(user);
	}

}
