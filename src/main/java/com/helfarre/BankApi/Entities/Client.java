package com.helfarre.BankApi.Entities;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.helfarre.BankApi.Entities.Role;
import com.helfarre.BankApi.Entities.compte_cheque;
import com.helfarre.BankApi.Entities.compte_epargne;

@Entity
@Table(name="client")
@PrimaryKeyJoinColumn(name = "person_id")
public class Client extends person {
	@Override
	public String toString() {
		return "Client [email=" + email + ", password=" + password + ", cin=" + cin + ", epargne=" + epargne
				+ ", cheque=" + cheque + "]";
	}

	@Column(name = "email")
	@NotNull(message = "email cannot be null.")
	@NotBlank(message = "email cannot be null.")
	@Email(message = "Enter a valid email address.") 
	private String email;

	@Column(name = "password")
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

	@JsonManagedReference(value="compteclient")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "client", orphanRemoval = true)
	private Set<compte_epargne> epargne;
	//@JsonBackReference
	@JsonManagedReference(value="compteclient")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "client", orphanRemoval = true)
	private Set<compte_cheque> cheque;

	@ManyToOne(cascade = CascadeType.DETACH, fetch= FetchType.EAGER)
	private Role role;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<compte_epargne> getEpargne() {
		return epargne;
	}

	public void setEpargne(Set<compte_epargne> epargne) {
		this.epargne = epargne;
	}

	public Set<compte_cheque> getCheque() {
		return cheque;
	}

	public void setCheque(Set<compte_cheque> cheque) {
		this.cheque = cheque;
	}

	
	public Client() {
		
		
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}











