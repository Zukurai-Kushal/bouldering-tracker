package com.fdm.bouldering_tracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
public class AppUser {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long userId;
	private String username;
	private String email;
	private String passwordHash;
	
	public AppUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AppUser(String username, String email, String passwordHash) {
		super();
		this.username = username;
		this.email = email;
		this.passwordHash = passwordHash;
	}

	public Long getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

}
