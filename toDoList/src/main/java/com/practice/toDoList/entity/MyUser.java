package com.practice.toDoList.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="users")
public class MyUser {
	
	@Id
	@Column(name="username")
	private String username;
	
	@Column(name= "password")
	private String password;
	
	@Column(name = "enabled")
	private boolean enabled;
	
	public MyUser(String username, String password,boolean enabled) {
		this.enabled = enabled;
		this.username = username;
		this.password = password;
	}

	public MyUser(){
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MyUser [username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append(", enabled=");
		builder.append(enabled);
		builder.append("]");
		return builder.toString();
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	
	
}
