package com.example.auth.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.auth.models.entites.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	 Optional<User> findByUsername(String username);
	 Optional<User> findByEmailIgnoreCase(String email);
		
}
