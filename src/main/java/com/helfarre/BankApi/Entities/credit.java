package com.helfarre.BankApi.Entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
@Entity
@Table(name="credit")
public class credit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	@Column(name = "id", updatable = false, nullable = false)
    private long id;
	@Column(name="nbmois")
	 private int nbmois;
	@Column(name="somme")
	 private int somme; 
	@Column(name="taux")
	 private float taux;
	@Column(name="mensualite")
	 private float mensualite;
	@Column(name="statut")
	 private boolean statut;
	@Column(name="moisrest")
	 private int moisrest;
	 public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public boolean isStatut() {
		return statut;
	}
	public void setStatut(boolean statut) {
		this.statut = statut;
	}
	public int getMoisrest() {
		return moisrest;
	}
	public void setMoisrest(int moisrest) {
		this.moisrest = moisrest;
	}
	@ManyToOne	
	 private compte credit;
	public int getNbmois() {
		return nbmois;
	}
	public void setNbmois(int nbmois) {
		this.nbmois = nbmois;
	}
	public int getSomme() {
		return somme;
	}
	public void setSomme(int somme) {
		this.somme = somme;
	}
	public float getTaux() {
		return taux;
	}
	public void setTaux(float taux) {
		this.taux = taux;
	}
	public float getMensualite() {
		return mensualite;
	}
	
	public void setMensualite(float mensualite) {
		this.mensualite = mensualite;
	}
	public compte getCredit() {
		return credit;
	}
	public void setCredit(compte credit) {
		this.credit = credit;
	}

}
