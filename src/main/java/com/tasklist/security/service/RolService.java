package com.tasklist.security.service;

import com.tasklist.security.enums.RolName;
import com.tasklist.security.model.Rol;
import com.tasklist.util.exception.InternalServerErrorException;
import com.tasklist.util.exception.NotFoundException;

public interface RolService {
	public Rol getRolByRolName(RolName rolName)throws NotFoundException, InternalServerErrorException;
	public void storeRol(Rol rol)throws InternalServerErrorException;
	public void deleteRol(Rol rol)throws NotFoundException, InternalServerErrorException;
}
