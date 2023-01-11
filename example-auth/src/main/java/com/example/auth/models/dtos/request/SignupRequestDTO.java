package com.example.auth.models.dtos.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.example.auth.utils.Constant;

public class SignupRequestDTO  implements Serializable{

	private static final long serialVersionUID = -7989177021996288156L;

	@NotBlank(message = "El nombre de usuario no puede estar vacio")
	private String username;

	@NotBlank(message = "El nombre no puede estar vacio")
	private String firstName;
	
	@NotBlank(message = "El apellido no puede estar vacio")
	private String lastName;
	
	@NotBlank(message = "La contraseña no puede estar vacio")
	private String password;
	
	@NotBlank(message = "El email no puede estar vacio")
	@Pattern(regexp = Constant.PATTER_EMAIL, message = "El formato del email no es valido")
	private String email;

	public SignupRequestDTO() {
	}

	public SignupRequestDTO(@NotBlank(message = "El nombre de usuario no puede estar vacio") String username,
			@NotBlank(message = "El nombre no puede estar vacio") String firstName,
			@NotBlank(message = "El apellido no puede estar vacio") String lastName,
			@NotBlank(message = "La contraseña no puede estar vacio") String password,
			@NotBlank(message = "El email no puede estar vacio") @Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$") String email) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserDTO [username=" + username + ", firstName=" + firstName + ", lastName=" + lastName + ", password="
				+ password + ", email=" + email + "]";
	}

}
