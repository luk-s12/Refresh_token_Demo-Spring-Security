package com.example.auth.exceptions;

import java.util.ArrayList;
import java.util.List;

public class SignupException extends RuntimeException {

	private static final long serialVersionUID = 705530062790280521L;

	List<String> errors = new ArrayList<>();
	
	public SignupException() {
		super();
	}
	
	public SignupException(String message, List<String> errors) {
		super(message);
		this.errors = errors;
		
	}
	public SignupException(String message) {
		super();
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	
	
}
