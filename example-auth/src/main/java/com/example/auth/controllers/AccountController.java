package com.example.auth.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.models.dtos.request.SignupRequestDTO;
import com.example.auth.services.UserService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("api")
@Api(value = "/users", tags = "0. Usuarios", description = "api para dar de alta a usuario")
public class AccountController {

    private static Logger log = LoggerFactory.getLogger(AccountController.class);
	
    @Autowired
    private UserService userService;
    
	@PostMapping("signup")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> register(@Valid @RequestBody SignupRequestDTO signupDTO) {
		log.info("Creando un nuevo usuario con el resquest: {}", signupDTO);
		this.userService.save(signupDTO);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
