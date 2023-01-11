package com.example.auth.security.services;

import com.example.auth.models.dtos.response.LoginResponseDTO;

public interface AuthService {
	
	void authentication(String login, String password); 
	LoginResponseDTO refreshToken(String refreshTokenRequest, String clientId);
	LoginResponseDTO login(String username, String clientId);

}

