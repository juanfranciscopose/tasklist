package com.tasklist.dto;

import java.util.HashSet;
import java.util.Set;

public class UserRequest {
	private long id;
	private String name;
	private String surname;
	private String email;
	private String password;
	private long telephone;
	private Set<String> roles;
	
	public UserRequest() {}
	
	public UserRequest(long id, String name, String surname, String email, String password, long telephone) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.telephone = telephone;
		this.roles = new HashSet<String>();
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getTelephone() {
		return telephone;
	}
	public void setTelephone(long telephone) {
		this.telephone = telephone;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	
	
}