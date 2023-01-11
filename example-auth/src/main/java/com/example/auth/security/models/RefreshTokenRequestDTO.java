package com.example.auth.security.models;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class RefreshTokenRequestDTO  implements Serializable{

	private static final long serialVersionUID = -6064680981334929110L;

	@NotBlank(message = "El campo clientId no puede estar vacio")
	private String clientId;
	
	@NotBlank(message = "El campo refreshToken no puede estar vacio")
	private String refreshToken;
	
	public RefreshTokenRequestDTO() {
	}

	public RefreshTokenRequestDTO(@NotBlank(message = "El campo clientId no puede estar vacio") String clientId,
			@NotBlank(message = "El campo refreshToken no puede estar vacio") String refreshToken) {
		this.clientId = clientId;
		this.refreshToken = refreshToken;
	}

	public String getClientId() {
		return clientId;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public String toString() {
		return "RefreshTokenRequestDTO [clientId=" + clientId + ", refreshToken=" + refreshToken + "]";
	}

}
