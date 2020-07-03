package com.helfarre.BankApi.Security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RefreshToken {

	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	@Column(name = "id", updatable = false, nullable = false)
    private Long id;

	private String token;
	
	@Column( unique = true)
	private String userId;

	public RefreshToken() {

	}

	public String getRefreshToken() {
		return token;
	}

	public void setRefreshToken(String refreshToken) {
		this.token = refreshToken;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
