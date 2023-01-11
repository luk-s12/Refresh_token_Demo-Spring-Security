package com.example.auth.exceptions;

public class RoleNotFoundException  extends RuntimeException{

	private static final long serialVersionUID = 6929292639386518754L;

	public RoleNotFoundException() {
		super("No se encontro el rol para la creacion del usuario");
	}
	
}
