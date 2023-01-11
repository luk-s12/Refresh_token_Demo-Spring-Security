package com.example.auth.security.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.auth.exceptions.EmptyRefreshTokenException;
import com.example.auth.exceptions.UserDeviceNotFoundException;
import com.example.auth.models.entites.RefreshToken;
import com.example.auth.models.entites.User;
import com.example.auth.models.entites.UserDevice;
import com.example.auth.repositories.RefreshTokenRepository;
import com.example.auth.security.jwt.JwtTokenUtil;
import com.example.auth.security.services.RefreshTokenService;
import com.example.auth.security.services.UserDeviceService;
import com.example.auth.security.utils.IEncryptorUtil;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

	private final static Logger log = LoggerFactory.getLogger(RefreshTokenServiceImpl.class);
	
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private IEncryptorUtil encryptorUtil;
	
	@Autowired
	private UserDeviceService userDeviceService;
	
	@Override
	@Transactional
	public void save(RefreshToken refreshToken){
		log.info("Guardando el refresh token: {}", refreshToken);
		this.refreshTokenRepository.save( refreshToken ); 
	}
	
	@Override
	@Transactional
	public void createRefreshToken(User user, String refreshToken, String clientId){
		log.info("Creando el token con los parametros: user: {}, refresh_token: {}, clientId: {}", user, refreshToken,  clientId);
				
		Optional<UserDevice> deviceOptional = this.userDeviceService.findByUserDevice(clientId, user.getUsername() );
		
		if( deviceOptional.isPresent() ) {
			UserDevice device = deviceOptional.get();
			if( device.getUser().getUsername().equals(user.getUsername()) ) {
				log.info("El usuario tiene el siguiente dispositivo registrado: {}", device );
				UUID userDeviceId = device.getId();
				this.deleteRefreshToken( userDeviceId );
			}			
		}
		
		UserDevice userDevice = this.userDeviceService.buildUserDevice(user, clientId);

  		RefreshToken refresh  = new RefreshToken();
  		refresh.setUser( user );
  		refresh.setRefreshToken( encryptorUtil.encode(refreshToken) );
  		refresh.setDateTimeExpiration( this.jwtTokenUtil.getExpirationDateFromToken(refreshToken).toInstant() );
  		refresh.setUserDevice( userDevice  );
		
  		this.save(refresh);
	}
	
	@Override
	@Transactional(readOnly = true)
	public RefreshToken findRefreshToken(String clientId, String username ) {
		log.info("Buscando el refres_token: {}", clientId);
		return this.refreshTokenRepository.findRefreshTokenByClientIdAndUsername(clientId, username)
										  .orElseThrow( ()-> new EmptyRefreshTokenException("El dispositivo: " + clientId + " es invalido para esta solicitud") );
	}
	
	@Override
	@Transactional
	public void deleteRefreshToken(UUID userDeviceId) {
		log.info("Procesando borrado del dispositivo con id: {} ", userDeviceId);
		Optional<RefreshToken> refreshTokenOptional = this.refreshTokenRepository.findByUserDevice( userDeviceId );
		
		if( refreshTokenOptional.isEmpty() )
			throw new UserDeviceNotFoundException("El dispositivo con el id " + userDeviceId + " no fue encontrado", userDeviceId);
			
		this.refreshTokenRepository.delete( refreshTokenOptional.get() );		
	}

	@Transactional
	@Scheduled(cron = "0 0 12 * * ?")
	public void expireRefreshToken() {
		log.info("Se activo la limpieza de refresh token expirados" );	
		Optional< List<RefreshToken> > expireTokens  = this.refreshTokenRepository.findExpireRefreshToken();
		
		if( expireTokens.isPresent()  && ! expireTokens.get().isEmpty() ) {
			List<UUID> ids = expireTokens.get().stream().map( RefreshToken :: getId ).collect(Collectors.toList());
			log.info("Se van borrar los siguientes refresh tokens expirados con los ids: " + ids );
			this.refreshTokenRepository.deleteAll( expireTokens.get() );	
		}
		
	}
	
	
}
