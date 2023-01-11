package com.example.auth.security.services.impl;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.auth.models.entites.User;
import com.example.auth.models.entites.UserDevice;
import com.example.auth.repositories.UserDeviceRepository;
import com.example.auth.security.services.UserDeviceService;


@Service
public class UserDeviceServiceImpl implements UserDeviceService {

	private static final Logger log = LoggerFactory.getLogger(UserDeviceServiceImpl.class);
	
	@Autowired
	private UserDeviceRepository userDeviceRepository;
	
	@Override
	@Transactional
	public void save(User user, String clientId) {
		log.info("Peticion de guardado del dispositivo: {} para el usuario: {}", clientId, user);
		UserDevice userDevice = this.buildUserDevice(user, clientId);
		this.userDeviceRepository.save(userDevice);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<UserDevice> findByUserDevice(String clientId, String username) {
		log.info("Peticion para buscar el dispositivo: {}", clientId);
		return this.userDeviceRepository.findByClientIdAndUsername(clientId, username);
	}
	
	@Override
	@Transactional
	public void deleteById(UUID id) {
		log.info("Se va a eliminar el dispositivo con id: {}", id);
		this.userDeviceRepository.deleteById(id);
	}
	
	@Override
	public UserDevice buildUserDevice(User user, String clientId) {
		UserDevice userDevice = new UserDevice();
		userDevice.setUser(user);
		userDevice.setClientId(clientId);
		return userDevice;
	}
	
}
