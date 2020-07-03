package com.helfarre.BankApi.Entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name= "cbancaire")
public class cbancaire implements Serializable  {

	private static final long serialVersionUID = 1L;


@Id
@GeneratedValue(strategy=GenerationType.IDENTITY) 
@Column(name = "id_carte")
private  Long id ;

@Column(name = "nom_carte")
private String nom_carte;




@Column(name = "num")
//@Pattern(regexp="^[0-9]{16}",message="length must be 16")
private String numcarte;

@Column(name = "expiration")
private Date expiration;


@Column(name = "retraitjour")
private int retraitjour;

@Column(name = "status")
private boolean status;

@Column(name = "mdp")
private int mdp;


public int getMdp() {
	return mdp;
}


public void setMdp(int mdp) {
	this.mdp = mdp;
}


public boolean isStatus() {
	return status;
}


public void setStatus(boolean status) {
	this.status = status;
}

@Column(name = "cvv")
@Pattern(regexp="^[0-9]{3}",message="length must be 3")
private String cvv;


@ManyToOne
@JsonBackReference(value="carte")
private compte_cheque compte;

public Date getExpiration() {
	return expiration;
}





public void setExpiration(Date date) {
	this.expiration = date;
}


public static long getSerialversionuid() {
	return serialVersionUID;
}

public int getRetraitjour() {
	return retraitjour;
}

public void setRetraitjour(int retraitjour) {
	this.retraitjour = retraitjour;
}


public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}


public String getNom_carte() {
	return nom_carte;
}

public void setNom_carte(String nom_carte) {
	this.nom_carte = nom_carte;
}

public String getNumcarte() {
	return numcarte;
}

public void setNumcarte(String numcarte) {
	this.numcarte = numcarte;
}



public compte_cheque getCompte() {
	return compte;
}

public String getCvv() {
	return cvv;
}

public void setCvv(String cvv) {
	this.cvv = cvv;
}



public void setCompte(compte_cheque compte) {
	this.compte = compte;
}

public cbancaire() {}


}
