package com.example.auth.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserAuthenticationException extends AuthenticationException {

    private static final long serialVersionUID = 7805825787920358822L;

	public UserAuthenticationException(String message) {
        super(message);
    }

}