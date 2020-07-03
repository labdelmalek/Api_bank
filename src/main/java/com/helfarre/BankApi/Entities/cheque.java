package com.helfarre.BankApi.Entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
@Entity
@Table(name= "cheque")
public class cheque implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(name = "id", updatable = false, nullable = false)
	 private long  id;
		@Column(name = "numcheque", updatable = true, nullable = false)
    private String  numcheque;
	@Column(name = "benificiaire", updatable = true, nullable = false)
	private String benificiaire;
	@Column(name = "date_encaissement", updatable = true, nullable = false)
	private Date Date_encaissement;
	@Column(name = "montant", updatable = true, nullable = false)
	private float montant;
	@Column(name = "numinternational", updatable = true, nullable = false)
	private String numinternational;
	@Column(name = "status", updatable = true, nullable = false)
	private int status;
	
	@OneToOne(cascade = CascadeType.DETACH, orphanRemoval = false)
	private banquier bq;

	@Column(name = "type", updatable = true, nullable = false) 
	private String typeencaissement;
/*	@Column(name = "rib", updatable = true, nullable = false)
	private String rib;*/
	public String getTypeencaissement() {
		return typeencaissement;
	}
	public void setTypeencaissement(String typeencaissement) {
		this.typeencaissement = typeencaissement;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public banquier getBq() {
		return bq;
	}

	public void setBq(banquier bq) {
		this.bq = bq;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

/*	public String getRib() {
		return rib;
	}
	public void setRib(String rib) {
		this.rib = rib;
	}*/
	public compte_cheque getCompte() {
		return compte;
	}
	public void setCompte(compte_cheque compte) {
		this.compte = compte;
	}
	public String getNumcheque() {
		return numcheque;
	}
	public void setNumcheque(String numcheque) {
		this.numcheque = numcheque;
	}
	public String getBenificiaire() {
		return benificiaire;
	}
	public void setBenificiaire(String benificiaire) {
		this.benificiaire = benificiaire;
	}
	public Date getDate_encaissement() {
		return Date_encaissement;
	}
	public void setDate_encaissement(Date date_encaissement) {
		Date_encaissement = date_encaissement;
	}
	public float getMontant() {
		return montant;
	}
	public void setMontant(float montant) {
		this.montant = montant;
	}
	
	public String getNuminternational() {
		return numinternational;
	}
	public void setNuminternational(String numinternational) {
		this.numinternational = numinternational;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public cheque() {
		super();
		// TODO Auto-generated constructor stub
	}
	@JsonBackReference(value="cartess")
	@ManyToOne
	private compte_cheque compte;
   
}
