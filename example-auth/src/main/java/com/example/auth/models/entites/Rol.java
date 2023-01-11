package com.example.auth.models.entites;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.auth.models.enums.RolEnum;

@Entity
@Table(name="roles")
public class Rol {
		
	@Id
	@Column(name = "name" , nullable = false, unique = true)
	@Enumerated(EnumType.STRING)
	private RolEnum name;

	public RolEnum getName() {
		return name;
	}

	public void setName(RolEnum name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Rol [name=" + name.name() + "]";
	}

}
