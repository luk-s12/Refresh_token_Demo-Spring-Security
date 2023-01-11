package com.example.auth.exceptions;

import java.util.UUID;

public class UserDeviceNotFoundException  extends RuntimeException{

	private static final long serialVersionUID = 900437790574978371L;
	
	private UUID userDeviceId;
	
	public UserDeviceNotFoundException(String message) {
		super(message);
	}
	public UserDeviceNotFoundException(String message, UUID userDeviceId) {
		super(message);
		this.userDeviceId = userDeviceId;
	}

	public UUID getUserDeviceId() {
		return userDeviceId;
	}

	public void setUserDeviceId(UUID userDeviceId) {
		this.userDeviceId = userDeviceId;
	}
	
}
