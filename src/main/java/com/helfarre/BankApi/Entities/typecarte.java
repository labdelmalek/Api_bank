package com.helfarre.BankApi.Entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name= "typecarte")
public class typecarte  implements Serializable{
	private static final long serialVersionUID = 1L;

@Id @GeneratedValue(strategy=GenerationType.AUTO) 
@Column(name = "id_carte", updatable = false, nullable = true)
private  Long id ;


@Column(name = "nom_carte", updatable = true, nullable = false)
private String nom;


@Column(name = "international", updatable = false, nullable = false)
private boolean international;


@Column(name = "plafond_ret", updatable = true, nullable = false)
private int plafond_retrait;


@Column(name = "plafond_paie", updatable = true, nullable = false)
private int plafond_paiement;


@Column(name = "prix_carte", updatable = true, nullable = false)
private float prix_carte;


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
public boolean isInternational() {
	return international;
}
public void setInternational(boolean international) {
	this.international = international;
}
public int getPlafond_retrait() {
	return plafond_retrait;
}
public void setPlafond_retrait(int plafond_retrait) {
	this.plafond_retrait = plafond_retrait;
}
public int getPlafond_paiement() {
	return plafond_paiement;
}
public void setPlafond_paiement(int plafond_paiement) {
	this.plafond_paiement = plafond_paiement;
}
public float getPrix_carte() {
	return prix_carte;
}
public void setPrix_carte(float prix_carte) {
	this.prix_carte = prix_carte;
}
public typecarte() {

}


}
