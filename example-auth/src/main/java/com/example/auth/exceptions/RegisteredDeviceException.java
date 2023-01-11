package com.example.auth.exceptions;

public class RegisteredDeviceException extends RuntimeException {

	private static final long serialVersionUID = -2724625872371322993L;
	
	private String clientId;
	
	public RegisteredDeviceException(String message) {
		super(message);
	}

	public RegisteredDeviceException(String message, String clientId) {
		super(message);
		this.clientId = clientId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

}
