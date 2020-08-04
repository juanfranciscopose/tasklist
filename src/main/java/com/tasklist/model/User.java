package com.tasklist.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tasklist.security.model.Rol;


/*
 * this class (USER) interacts with the database 
 */

@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="name", nullable = false, length = 150)
	private String name;
	
	@Column(name="surname", nullable = false, length = 200)
	private String surname;
	
	@Column(name="email", nullable = false, length = 100, unique = true)
	private String email;
	
	@Column(name="password", nullable = false, length = 255)
	private String password;
	
	@Column(name="telephone", length = 20)
	private long telephone;
	
	@OneToMany(mappedBy = "author", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Task> tasks;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_rol", joinColumns = @JoinColumn(name="user_id"), 
				inverseJoinColumns = @JoinColumn(name= "rol_id"))
	private Set<Rol> rols;
	
	public User() {}

	public User(String name, String surname, String email, String password, long telephone) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.telephone = telephone;
		this.tasks = new ArrayList<Task>();
		this.rols = new HashSet<Rol>();
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
	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	public void addTask(Task task) {
		this.tasks.add(task);
		task.setAuthor(this);
	}
	
	public void removeTask(Task task) {
		this.tasks.remove(task);
		task.setAuthor(null);
	}

	public Set<Rol> getRols() {
		return rols;
	}

	public void setRols(Set<Rol> rols) {
		this.rols = rols;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", password="
				+ password + ", telephone=" + telephone + ", tasks=" + tasks + ", rols=" + rols + "]";
	}
}
