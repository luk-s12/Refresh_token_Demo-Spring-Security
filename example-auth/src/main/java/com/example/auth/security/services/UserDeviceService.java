package com.example.auth.security.services;

import java.util.Optional;
import java.util.UUID;

import com.example.auth.models.entites.User;
import com.example.auth.models.entites.UserDevice;

public interface UserDeviceService {

	void save(User user, String clientId);
	void deleteById(UUID id);	
	Optional<UserDevice> findByUserDevice(String clientId, String username);
	UserDevice buildUserDevice(User user, String clientId);
	
	
}
