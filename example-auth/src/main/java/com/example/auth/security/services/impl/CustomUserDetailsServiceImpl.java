package com.example.auth.security.services.impl;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.auth.exceptions.UserAuthenticationException;
import com.example.auth.models.entites.User;
import com.example.auth.repositories.UserRepository;
import com.example.auth.security.services.CustomUserDetailsService;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

	private static Logger log = LoggerFactory.getLogger(CustomUserDetailsServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Obteniendo el usuario del servicio CustomUserDetailsService. Usuario con el login o email : {}", username);
		Supplier<UsernameNotFoundException> exception = () -> new UsernameNotFoundException("Error al autenticarse con el usuario " + username);
		
		User user = this.userRepository.findByUsername(username)
					.orElseGet(() -> this.userRepository.findByEmailIgnoreCase(username)
					.orElseThrow(exception));
		
		if( ! user.getEnabled() )
			throw new UserAuthenticationException("Temporalmente no se encuentra habilitada tu cuenta");
		
        List<GrantedAuthority> grantedAuthorities = user
                .getRoles()
                .stream()
                .map(authority -> new SimpleGrantedAuthority( authority.getName().name() ))
                .collect(Collectors.toList());
        
       return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
	}	
	
	
	public UserDetails getContextUser() {
		UserDetails userDetails = null;
		Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(user instanceof UserDetails) 
			userDetails = (UserDetails) user;
		
		return userDetails;	
	}
	
}