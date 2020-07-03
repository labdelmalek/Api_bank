package com.helfarre.BankApi.Entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="infocredit")
public class infocredit {
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
	@Column(name="nom")
	
	private String nom;

	@Column(name="prenom")
	
	private String prenom;

	@Column(name="cin")
	
	private String cin;
	@Column(name="salaire")
	private float salaire;

public float getSalaire() {
		return salaire;
	}
	public void setSalaire(float salaire) {
		this.salaire = salaire;
	}


@Column(name="typesalarié")
	// salarie ou retraité
	private  String typeemployee ;
@Column(name="sommeclient")
private  float sommeclient ;
@Column(name="typecredit")
private  String typecredit ;
@Column(name="montantcredit")
private  float montantcredit ;
@Column(name="nbremois")
private  int nombremois ;
@Column(name="mensualité")
private  float mensualite ;

@Column(name="datedemande")
private  Date datedemande ;

@Column(name="idagence")
private  Long idagence ;

@Column(name="idcredit")
private  Long idcredit ;
public Long getIdcredit() {
	return idcredit;
}
public void setIdcredit(Long idcredit) {
	this.idcredit = idcredit;
}
public Long getIdagence() {
	return idagence;
}
public void setIdagence(Long idagence) {
	this.idagence = idagence;
}
public float getMensualite() {
	return mensualite;
}
public void setMensualite(float mensualite) {
	this.mensualite = mensualite;
}
public Date getDatedemande() {
	return datedemande;
}
public void setDatedemande(Date datedemande) {
	this.datedemande = datedemande;
}
public infocredit() {
	super();
	// TODO Auto-generated constructor stub
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getNom() {
	return nom;
}
public void setNom(String nom) {
	this.nom = nom;
}
public String getPrenom() {
	return prenom;
}
public void setPrenom(String prenom) {
	this.prenom = prenom;
}
public String getCin() {
	return cin;
}
public void setCin(String cin) {
	this.cin = cin;
}
public String getTypeemployee() {
	return typeemployee;
}
public void setTypeemployee(String typeemployee) {
	this.typeemployee = typeemployee;
}
public float getSommeclient() {
	return sommeclient;
}
public void setSommeclient(float sommeclient) {
	this.sommeclient = sommeclient;
}
public String getTypecredit() {
	return typecredit;
}
public void setTypecredit(String typecredit) {
	this.typecredit = typecredit;
}
public float getMontantcredit() {
	return montantcredit;
}
public void setMontantcredit(float montantcredit) {
	this.montantcredit = montantcredit;
}
public int getNombremois() {
	return nombremois;
}
public void setNombremois(int nombremois) {
	this.nombremois = nombremois;
}


public static long getSerialversionuid() {
	return serialVersionUID;
}



	
}
