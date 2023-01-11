package com.example.auth.models.entites;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_devices")
public class UserDevice {

	@Id
	@GeneratedValue
	private UUID id;
	
	@ManyToOne
	private User user;
	
	@Column(name = "client_id", nullable = false)
	private String clientId;
	
	@OneToOne(mappedBy = "userDevice")
	private RefreshToken refreshToken;

	public UUID getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public String getClientId() {
		return clientId;
	}

	public RefreshToken getRefreshToken() {
		return refreshToken;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setRefreshToken(RefreshToken refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public String toString() {
		return "UserDevice [id=" + id + ", user=" + user + ", clientId=" + clientId + "]";
	}
	
}
