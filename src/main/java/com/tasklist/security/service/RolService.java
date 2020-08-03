package com.tasklist.security.service;

import com.tasklist.security.enums.RolName;
import com.tasklist.security.model.Rol;
import com.tasklist.util.exception.InternalServerErrorException;
import com.tasklist.util.exception.NotFoundException;

public interface RolService {
	public Rol getRolByRolName(RolName rolName)throws NotFoundException, InternalServerErrorException;
	public void storeRol(RolName rolName)throws InternalServerErrorException;
	public void deleteRol(RolName rolName)throws NotFoundException, InternalServerErrorException;
}
