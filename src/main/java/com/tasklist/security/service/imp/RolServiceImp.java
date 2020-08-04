package com.tasklist.security.service.imp;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tasklist.security.dao.RolRepository;
import com.tasklist.security.enums.RolName;
import com.tasklist.security.model.Rol;
import com.tasklist.security.service.RolService;
import com.tasklist.util.exception.InternalServerErrorException;
import com.tasklist.util.exception.NotFoundException;

@Service
public class RolServiceImp implements RolService{
	
	@Autowired
	private RolRepository rolRepository;
	
	@Override
	public Rol getRolByRolName(RolName rolName) throws NotFoundException, InternalServerErrorException {
		try {
			Rol rol = rolRepository.findByRol(rolName).get();
			return rol;
		} catch (NoSuchElementException e) {
			throw new NotFoundException("rol with name: "+rolName+" not exist");
		} catch (Exception e) {
			 throw new InternalServerErrorException(e.toString());
		}
	}

	@Override
	public void storeRol(RolName rolName) throws InternalServerErrorException {
		try {
			Rol rol = new Rol(rolName);
			rolRepository.save(rol);
		} catch (Exception e) {
			throw new InternalServerErrorException(e.toString());
		}
	}

	@Override
	public void deleteRol(RolName rolName) throws NotFoundException, InternalServerErrorException {
		try {
			Rol rol = rolRepository.findByRol(rolName).get();
			rolRepository.delete(rol);
		} catch (NoSuchElementException e) {
			throw new NotFoundException("rol with name: "+rolName+" not exist");
		} catch (Exception e) {
			 throw new InternalServerErrorException(e.toString());
		}
	}
	
}
