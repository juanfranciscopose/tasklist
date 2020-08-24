package com.tasklist.security.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tasklist.security.enums.RoleName;
import com.tasklist.security.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	public Optional<Role> findByRole(RoleName role);
}
