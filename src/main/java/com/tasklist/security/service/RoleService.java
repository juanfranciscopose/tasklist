package com.tasklist.security.service;

import com.tasklist.security.enums.RoleName;
import com.tasklist.security.model.Role;
import com.tasklist.util.exception.InternalServerErrorException;
import com.tasklist.util.exception.NotFoundException;

public interface RoleService {
	public Role getRoleByRoleName(RoleName roleName)throws NotFoundException, InternalServerErrorException;
	public void storeRole(Role role)throws InternalServerErrorException;
	public void deleteRole(Role role)throws NotFoundException, InternalServerErrorException;
}
