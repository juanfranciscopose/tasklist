package com.tasklist.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tasklist.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public Optional<User> findByEmailAndPassword(String email, String password);
	public Optional<User> findByEmail(String email);
	public boolean existsByEmail(String email);
}
