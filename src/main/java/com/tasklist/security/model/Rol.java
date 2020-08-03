package com.tasklist.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tasklist.security.enums.RolName;

@Entity
@Table(name="rols")
public class Rol {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Enumerated(EnumType.STRING)
	@Column(name="rol", nullable = false)
	private RolName rol;

	public Rol() {}
	
	public Rol(RolName rol) {
		this.rol = rol;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RolName getRol() {
		return rol;
	}

	public void setRol(RolName rol) {
		this.rol = rol;
	}
}
