package com.example.auth.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.auth.models.entites.Rol;
import com.example.auth.models.enums.RolEnum;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

	Optional<Rol>  findByName(RolEnum name);
	
}
