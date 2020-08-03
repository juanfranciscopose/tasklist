package com.tasklist.security.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tasklist.security.enums.RolName;
import com.tasklist.security.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer>{
	public Optional<Rol> findByRolName(RolName rolName);
}
