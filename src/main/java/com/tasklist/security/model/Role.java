package com.tasklist.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tasklist.security.enums.RoleName;

@Entity
@Table(name="roles")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Enumerated(EnumType.STRING)
	@Column(name="role", nullable = false)
	private RoleName role;

	public Role() {}
	
	public Role(RoleName role) {
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RoleName getRole() {
		return role;
	}

	public void setRole(RoleName role) {
		this.role = role;
	}
}
