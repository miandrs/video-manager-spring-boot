package com.miandrs.models.dto;

import java.util.UUID;

public class AuthDto {
	private String token;
	private UUID userId;

	public AuthDto(String token, UUID id) {
		this.token = token;
		this.userId = id;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}
}
