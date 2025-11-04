package com.fdm.bouldering_tracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long user_id;
	private String username;
	private String email;
	private String password_hash;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String username, String email, String password_hash) {
		super();
		this.username = username;
		this.email = email;
		this.password_hash = password_hash;
	}

	public Long getUser_id() {
		return user_id;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword_hash() {
		return password_hash;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword_hash(String password_hash) {
		this.password_hash = password_hash;
	}
	
}
