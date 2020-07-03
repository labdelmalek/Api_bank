package com.helfarre.BankApi.Entities;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="banquier")
@PrimaryKeyJoinColumn(name = "id_person")
public class banquier extends person {
private static final long serialVersionUID = 1L;
	
	
	
	@Column(name="email")
    @NotNull(message = "email cannot be null.")
	@Email(message = "Enter a valid email address.") 
	private String email;
 
	@Column(name="password")
	private String password;

	@Column(name="cin",unique=true)
	//@NotNull(message = "CIN cannot be null.") 
	//@NotBlank()
	//@Size(min=7, max=15,message = "enter a valid CIN.")
	//@Pattern(regexp="^[a-zA-Z]{1,2}[0-9].*[0-9]$", message="enter a valid CIN ,check your national letter .")
	private String cin;
	
	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	@ManyToOne
	@JoinColumn(name="agency_id")
	@JsonBackReference(value="banker")
	private Agence agence; 
	 
	

	@ManyToOne(cascade = CascadeType.DETACH, fetch= FetchType.EAGER)
	private Role role;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public banquier() {
		
		
	}

	public Agence getAgence() {
		return agence;
	}

	public void setAgence(Agence agence) {
		this.agence = agence;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	


	


	
	
	
	
}











