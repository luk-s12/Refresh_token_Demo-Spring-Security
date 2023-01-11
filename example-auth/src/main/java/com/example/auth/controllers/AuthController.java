package com.example.auth.controllers;

import javax.validation.Valid;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.models.dtos.request.LoginRequestDTO;
import com.example.auth.models.dtos.response.LoginResponseDTO;
import com.example.auth.security.models.RefreshTokenRequestDTO;
import com.example.auth.security.services.AuthService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("api/auth")
@Api(value = "/auth", tags = "1. Autorizacion", description = "apis para autenticarse y renovar los tokens")
public class AuthController {
	
	private final static Logger log = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/login")
	public ResponseEntity< LoginResponseDTO > login( @Valid @RequestBody LoginRequestDTO login ){
		log.info("Procesando peticion para el inicio de sesion con el request: {}", login);
		this.authService.authentication(login.getUsername(), login.getPassword());
		LoginResponseDTO response 		= this.authService.login( login.getUsername(), login.getClientId());	
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/refresh")
	public ResponseEntity< LoginResponseDTO  > getRefreshToken(@Valid @RequestBody  RefreshTokenRequestDTO request){
		log.info("En proceso para obtener nuevos token con el refresh_token: {}", request);
		LoginResponseDTO response =  this.authService.refreshToken( request.getRefreshToken(), request.getClientId());
		return  ResponseEntity.ok().body( response );
	} 
}



