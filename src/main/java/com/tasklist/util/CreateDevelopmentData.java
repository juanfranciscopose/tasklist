package com.tasklist.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class CreateDevelopmentData	implements CommandLineRunner{
	private static final Logger logger = LoggerFactory.getLogger(CreateDevelopmentData.class);
	/*@Autowired
	private RolService rolService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;*/
	
	@Override
	public void run(String... args) throws Exception {
		/*Rol admin = new Rol(RolName.ROL_ADMIN);
		Rol user = new Rol(RolName.ROL_USER);
		rolService.storeRol(admin);
		rolService.storeRol(user);
		logger.info("Roles saved in database");
		User oneAdmin = new User("Admin", "Admin", "admin@gmail.com", "admin123", 0);
		
		//encoder pass
		oneAdmin.setPassword(passwordEncoder.encode(oneAdmin.getPassword()));
		Set<Rol> roles = new HashSet<>();
		roles.add(user);
		roles.add(admin);
		oneAdmin.setRols(roles);
		userRepository.save(oneAdmin);
		
		logger.info("Roles and admin user saved in database");*/
		//logger.warn("Roles and admin user have already been added. They are commented");
		logger.warn("testing mode: only create roles");
	}

}
