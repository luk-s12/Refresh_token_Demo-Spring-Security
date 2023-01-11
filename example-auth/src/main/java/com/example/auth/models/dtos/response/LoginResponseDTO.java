package com.example.auth.models.dtos.response;

import java.io.Serializable;

public class LoginResponseDTO implements Serializable{
	
	private static final long serialVersionUID = -4043444486463174220L;
	
	private String access_token;
	
	private String refresh_token;

	public LoginResponseDTO() {
	}
	
	public LoginResponseDTO(String access_token, String refresh_token) {
		this.access_token = access_token;
		this.refresh_token = refresh_token;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	@Override
	public String toString() {
		return "LoginResponseDTO [access_token=" + access_token + ", refresh_token=" + refresh_token + "]";
	}

	
}
