package com.example.auth.exceptions;

public class EmptyRefreshTokenException extends RuntimeException{

	private static final long serialVersionUID = 7697544140206728149L;
	
	public EmptyRefreshTokenException(String message) {
		super(message);
	}
	
}
