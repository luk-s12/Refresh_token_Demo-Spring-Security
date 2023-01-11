package com.example.auth.security.services;

import java.util.UUID;

import com.example.auth.models.entites.RefreshToken;
import com.example.auth.models.entites.User;


public interface RefreshTokenService {

	void deleteRefreshToken(UUID userDeviceId);
	void save(RefreshToken refreshToken);
	void createRefreshToken(User user, String refreshToken, String clientId);
	RefreshToken findRefreshToken(String clientId, String username);
	
}

