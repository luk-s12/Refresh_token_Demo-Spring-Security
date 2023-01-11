package com.example.auth.services;

import com.example.auth.models.dtos.request.SignupRequestDTO;
import com.example.auth.models.entites.User;

public interface UserService {

	void save(SignupRequestDTO signupDTO);
	User findUserByUsername(String username);

}
