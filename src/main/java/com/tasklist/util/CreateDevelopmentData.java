package com.tasklist.util;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.tasklist.dao.UserRepository;
import com.tasklist.model.User;
import com.tasklist.security.enums.RolName;
import com.tasklist.security.model.Rol;
import com.tasklist.security.service.RolService;
import com.tasklist.util.exception.InternalServerErrorException;

@Component
public class CreateDevelopmentData	implements CommandLineRunner{
	private static final Logger logger = LoggerFactory.getLogger(CreateDevelopmentData.class);
	@Autowired
	private RolService rolService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void run(String... args) throws Exception {
		/*Rol admin = new Rol(RolName.ROL_ADMIN);
		Rol user = new Rol(RolName.ROL_USER);
		User oneAdmin = new User("Admin", "Admin", "admin@gmail.com", "admin123", 0);
		//encoder pass
		oneAdmin.setPassword(passwordEncoder.encode(oneAdmin.getPassword()));
		Set<Rol> roles = new HashSet<>();
		roles.add(user);
		roles.add(admin);
		oneAdmin.setRols(roles);
		rolService.storeRol(admin);
		rolService.storeRol(user);
		userRepository.save(oneAdmin);
		logger.info("Roles and admin user saved in database");*/
		logger.warn("Roles and admin user have already been added. They are commented");;
	}

}
