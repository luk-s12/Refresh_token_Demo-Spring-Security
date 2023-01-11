package com.example.auth.models.dtos.request;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;

public class LoginRequestDTO implements Serializable{

	private static final long serialVersionUID = 124417906914985097L;
	
	@NotBlank(message = "El nombre de usuario o el email no puede estar vacio")
	private String username;

	@NotBlank(message = "El campo de la contraseña no puede estar vacio")
	private String password;
	
	@NotBlank(message = "El campo clientId no puede estar vacio")
	private String clientId;
	
	public LoginRequestDTO() {
	}

	public LoginRequestDTO(@NotBlank(message = "El nombre de usuario o el email no puede estar vacio") String username,
			@NotBlank(message = "El campo de la contraseña no puede estar vacio") String password,
			@NotBlank(message = "El campo clientId no puede estar vacio") String clientId) {
		super();
		this.username = username;
		this.password = password;
		this.clientId = clientId;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getClientId() {
		return clientId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@Override
	public String toString() {
		return "LoginRequestDTO [username=" + username + ", password=" + password + ", clientId=" + clientId + "]";
	}

}
