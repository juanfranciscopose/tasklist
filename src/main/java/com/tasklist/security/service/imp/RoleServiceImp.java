package com.tasklist.security.service.imp;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tasklist.security.dao.RoleRepository;
import com.tasklist.security.enums.RoleName;
import com.tasklist.security.model.Role;
import com.tasklist.security.service.RoleService;
import com.tasklist.util.exception.InternalServerErrorException;
import com.tasklist.util.exception.NotFoundException;

@Service
public class RoleServiceImp implements RoleService{
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Role getRoleByRoleName(RoleName roleName) throws NotFoundException, InternalServerErrorException {
		try {
			Role role = roleRepository.findByRole(roleName).get();
			return role;
		} catch (NoSuchElementException e) {
			throw new NotFoundException("role with name: "+roleName+" not exist");
		} catch (Exception e) {
			 throw new InternalServerErrorException(e.toString());
		}
	}

	@Override
	public void storeRole(Role role) throws InternalServerErrorException {
		try {
			roleRepository.save(role);
		} catch (Exception e) {
			throw new InternalServerErrorException(e.toString());
		}
	}

	@Override
	public void deleteRole(Role role) throws NotFoundException, InternalServerErrorException {
		try {
			roleRepository.delete(role);
		} catch (NoSuchElementException e) {
			throw new NotFoundException("rol with name: "+role.getRole()+" not exist");
		} catch (Exception e) {
			 throw new InternalServerErrorException(e.toString());
		}
	}
	
}
