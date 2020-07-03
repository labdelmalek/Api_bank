package com.helfarre.BankApi.Security;

public class jwtResponse {

	private final String jwttoken;
	
	public jwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}
	public String getToken() {
		return this.jwttoken;
	}
}
