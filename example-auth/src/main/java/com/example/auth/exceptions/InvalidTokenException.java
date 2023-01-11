package com.example.auth.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidTokenException extends AuthenticationException {

	private static final long serialVersionUID = 1977971399797009744L;
	
	private String token;

	private String user;

	public InvalidTokenException(String message) {
		super(message);
	}
	
	public InvalidTokenException(String message, String token, String user) {
		super(message);
		this.token = token;
		this.user = user;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	
}
