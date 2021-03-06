package com.tasklist.security.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tasklist.model.User;

/*
 * - like user wrapper
 * - This class (Main User) interacts with the framework and has security functionality.
 */

@SuppressWarnings("serial")
public class MainUser implements UserDetails{

	private long id;
	private String email;
	private String name;
	private String surname;
	private String password;
	private long telephone;
	private Collection<? extends GrantedAuthority> authority;
	
	public static MainUser buildMainUserFromUser(User user) {
		List<GrantedAuthority> authorities = 
				user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRole().name()))
																		.collect(Collectors.toList());
		return new MainUser(user.getId(), user.getEmail(), user.getName(), user.getSurname(), user.getPassword()
				, user.getTelephone(), authorities);
	}
	
	public MainUser(long id, String email, String name, String surname, String password, long telephone,
			Collection<? extends GrantedAuthority> authority) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.telephone = telephone;
		this.authority = authority;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authority; 
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
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

	public long getTelephone() {
		return telephone;
	}

	public void setTelephone(long telephone) {
		this.telephone = telephone;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	

}
