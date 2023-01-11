package com.example.auth.models.entites;

import java.time.Instant;

import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {
	
	@Id
	@GeneratedValue
	private UUID id;

	@Lob
	@Column(name="refresh_token", nullable = false)
	private String refreshToken;
	
	@Column(name="datetime_expiration", nullable = false)
	private Instant dateTimeExpiration;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id", name = "user_id", nullable = false)
	private User user;
	
	@OneToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(referencedColumnName = "id", name="user_device_id", nullable = false)
	private UserDevice userDevice;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Instant getDateTimeExpiration() {
		return dateTimeExpiration;
	}

	public void setDateTimeExpiration(Instant dateTimeExpiration) {
		this.dateTimeExpiration = dateTimeExpiration;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserDevice getUserDevice() {
		return userDevice;
	}

	public void setUserDevice(UserDevice userDevice) {
		this.userDevice = userDevice;
	}

	@Override
	public String toString() {
		return "RefreshToken [id=" + id + ", refreshToken=" + refreshToken + ", dateTimeExpiration="
				+ dateTimeExpiration + ", user=" + user + ", userDevice=" + userDevice + "]";
	}
	
	
}
