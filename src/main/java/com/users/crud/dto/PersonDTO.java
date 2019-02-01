package com.users.crud.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public abstract class PersonDTO {
	
	private long id;
	@NotNull
	private String name;
	@NotNull @Email
	private String email;
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
