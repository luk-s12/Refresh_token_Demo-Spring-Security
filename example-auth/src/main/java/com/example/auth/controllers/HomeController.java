package com.example.auth.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("api/home")
@Api(value = "/home", tags = "2. Inicio",  description = "apis para probar los roles del usuario")
public class HomeController {

	@GetMapping("user")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity< Map<String,  String> > homeUser(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String message 	 = "Accediste a la home para el rol user";
		UserDetails user = null;
		Map<String,  String> response = new HashMap<>();
		
		if(principal instanceof UserDetails) {
			user = (UserDetails) principal;
			message += ". Ususario: " + user.getUsername() + ". Autoridades: " + user .getAuthorities();
		}

		response.put("message", message);

		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity< Map<String,  String> > homeAdmin(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String message 	 = "Accediste a la home para el rol admin";
		UserDetails user = null;
		Map<String,  String> response = new HashMap<>();		
		
		if(principal instanceof UserDetails) {
			user = (UserDetails) principal;
			message += ". Ususario: " + user.getUsername() + ". Autoridades: " + user .getAuthorities();
		}
		
		response.put("message", message);
		
		return ResponseEntity.ok().body(response);
	}
	
	
}
