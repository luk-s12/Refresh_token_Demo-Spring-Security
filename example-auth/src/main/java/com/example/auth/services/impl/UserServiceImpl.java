package com.example.auth.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.auth.exceptions.RoleNotFoundException;
import com.example.auth.exceptions.SignupException;
import com.example.auth.models.dtos.request.SignupRequestDTO;
import com.example.auth.models.entites.Rol;
import com.example.auth.models.entites.User;
import com.example.auth.models.enums.RolEnum;
import com.example.auth.repositories.RolRepository;
import com.example.auth.repositories.UserRepository;
import com.example.auth.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger log = LoggerFactory.getLogger( UserServiceImpl.class ); 
	
	@Value("${password.encode.magic-phrase}")
	private String passowrdMagicPhrase;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RolRepository rolRepository;
	
	@Autowired
	private  PasswordEncoder bCryptPasswordEncoder;

	@Override
	@Transactional
	public void save(SignupRequestDTO signupDTO)  {
		log.info("Solicitud para guardar al usuario: {}", signupDTO);
		
		Optional<Rol> rol = this.rolRepository.findByName( RolEnum.ROLE_USER );
		
		if( rol.isEmpty() )
			throw new RoleNotFoundException();
		
		Optional<User> userByEmail =  this.userRepository.findByEmailIgnoreCase( signupDTO.getEmail() );
		Optional<User> username    =  this.userRepository.findByUsername( signupDTO.getUsername() );		
		List<String> errors 	   = new ArrayList<>();
		
		if( userByEmail.isPresent() ) 
			errors.add("El email ya esta registrado");
	
		if( username.isPresent() )
			errors.add("Nombre del usuario ya esta registrado");
			
		if( ! errors.isEmpty() )
			throw new SignupException("Ocurrio errores con los campos para el registro de usuario", errors );

		User user = new User();
		
		user.setUsername(  signupDTO.getUsername()  );
		user.setFirstName( signupDTO.getFirstName() );
		user.setLastName(  signupDTO.getLastName()  );
		user.setEmail( 	   signupDTO.getEmail() 	);
		user.setPassword( bCryptPasswordEncoder.encode( signupDTO.getPassword() + this.passowrdMagicPhrase ));
		user.setRoles( new HashSet<>(Arrays.asList(rol.get())) );
		user.setEnabled(true);

		this.userRepository.save(user);

	}

	@Override
	@Transactional(readOnly = true)
	public User findUserByUsername(String username){
		log.info("Solicitud para buscar al usuario: {}", username);		
		return this.userRepository.findByUsername(username)
				   .orElseThrow(() -> new UsernameNotFoundException("Se produjo un error con el usuario " + username));
				
	}
	
}