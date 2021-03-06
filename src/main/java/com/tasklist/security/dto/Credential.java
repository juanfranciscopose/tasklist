package com.tasklist.security.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class Credential {
	private long userId;
	private String token;
	private String bearer;
	private String username;
	private Collection<? extends GrantedAuthority> authorities;
	
	
	public Credential(long id, String token, String username, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.setUserId(id);
		this.token = token;
		this.username = username;
		this.bearer = "Bearer";
		this.authorities = authorities;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getBearer() {
		return bearer;
	}
	public void setBearer(String bearer) {
		this.bearer = bearer;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}
