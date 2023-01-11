package com.example.auth.exceptions;

public class EmailAlreadyUsedException extends RuntimeException {

    private static final long serialVersionUID = 6759664982430744850L;

	public EmailAlreadyUsedException() {
        super("El email ya esta registrado");
    }
}
