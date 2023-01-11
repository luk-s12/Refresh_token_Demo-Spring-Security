package com.example.auth.security.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.auth.exceptions.InvalidTokenException;
import com.example.auth.exceptions.UserAuthenticationException;
import com.example.auth.models.dtos.response.LoginResponseDTO;
import com.example.auth.models.entites.RefreshToken;
import com.example.auth.models.entites.User;

import com.example.auth.security.jwt.JwtTokenUtil;
import com.example.auth.security.services.AuthService;
import com.example.auth.security.services.RefreshTokenService;
import com.example.auth.security.utils.IEncryptorUtil;
import com.example.auth.services.UserService;

@Service
public class AuthServiceImpl implements AuthService{
	
	private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
	
	@Value("${password.encode.magic-phrase}")
	private String passowrdMagicPhrase;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private RefreshTokenService refreshTokenService;
	
	@Autowired
	private IEncryptorUtil encryptorUtil;
	
	@Override
	public void authentication(String login, String password) {
		log.info("Verificacion el usuario: {} y la password: {}", login, password);
		
		try {
		
			authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(login, password + this.passowrdMagicPhrase) );
		
		}catch(BadCredentialsException e) {
			log.warn("El usuario ingreso mal el usuario o contraseña para autenticarse o esta queriendo acceder con un dispositivo que no le corresponde");
			throw new UserAuthenticationException("Credenciales no validas. Vuelva a iniciar sesion y revise que el usuario y constrasenia sean correctas");
		}			
	}

	@Override
	@Transactional
	public LoginResponseDTO login(String username, String clientId) {
		log.info("iniciando sesion con el userdatils: {} , nombre de usuario: {} y dispositivo con id: {}", username, clientId);

		User   user 		  = this.userService.findUserByUsername(username);
		String access_token   = this.jwtTokenUtil.generateToken(user, username);
		String refresh_token  = this.jwtTokenUtil.generateRefreshToken(username);
		
		this.refreshTokenService.createRefreshToken(user, refresh_token, clientId);		
		
		return new LoginResponseDTO(access_token, refresh_token);
		
	}

	@Override
	@Transactional
	public LoginResponseDTO refreshToken(String refreshTokenRequest, String clientId ) {
		log.info("Solicitando refresh token con el token: {} y el dispositivo: {}", refreshTokenRequest,  clientId);

		String username 				  = this.jwtTokenUtil.getUsernameFromToken(refreshTokenRequest);	
		RefreshToken refreshTokenOptional = this.refreshTokenService.findRefreshToken( clientId, username );
		
		if( ! this.encryptorUtil.matches( refreshTokenRequest , refreshTokenOptional.getRefreshToken()) ) {
			log.warn("Se esta pidiendo un nuevo refresh token con un dispositivo no valido para el usuario");
			throw new InvalidTokenException("El cliente " + clientId + " no es valido para esta solicitud");
		}
		
		return this.login( username , clientId);
	}

	
}
