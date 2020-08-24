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
import com.tasklist.security.enums.RoleName;
import com.tasklist.security.model.Role;
import com.tasklist.security.service.RoleService;


@Component
public class CreateDevelopmentData	implements CommandLineRunner{
	private static final Logger logger = LoggerFactory.getLogger(CreateDevelopmentData.class);
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void run(String... args) throws Exception {
		Role admin = new Role(RoleName.ROLE_ADMIN);
		Role user = new Role(RoleName.ROLE_USER);
		roleService.storeRole(admin);
		roleService.storeRole(user);
		User oneAdmin = new User("Admin", "Admin", "admin@gmail.com", "admin123", 0);
		
		//encoder pass
		oneAdmin.setPassword(passwordEncoder.encode(oneAdmin.getPassword()));
		Set<Role> roles = new HashSet<Role>();
		roles.add(user);
		roles.add(admin);
		oneAdmin.setRoles(roles);
		userRepository.save(oneAdmin);
		
		//----messages---
		logger.info("Roles and admin user saved in database");
		//logger.warn("Roles and admin user have already been added. They are commented");
		//logger.warn("testing mode: only create roles");
		//logger.info("Roles saved in database");
	}

}
