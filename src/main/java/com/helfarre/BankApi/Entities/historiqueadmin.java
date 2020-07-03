package com.helfarre.BankApi.Entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="historique")
public class historiqueadmin {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
	
	@Column(name="date")
	
	
	private Date jour;
	
	@Column(name="soldeagence")
	private double soldeagence;
	
	@Column(name="soldeclients")
	private double soldeclients;
	@Column(name="restecredit")
	private double restecredit;
	@Column(name="idagence")
	private long idagence;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setIdagence(long idagence) {
		this.idagence = idagence;
	}
	public Date getJour() {
		return jour;
	}
	public void setJour(Date jour) {
		this.jour = jour;
	}
	public double getSoldeagence() {
		return soldeagence;
	}
	public void setSoldeagence(double soldeagence) {
		this.soldeagence = soldeagence;
	}
	public double getSoldeclients() {
		return soldeclients;
	}
	public void setSoldeclients(double soldeclients) {
		this.soldeclients = soldeclients;
	}
	public double getRestecredit() {
		return restecredit;
	}
	public void setRestecredit(double restecredit) {
		this.restecredit = restecredit;
	}
	public Long getIdagence() {
		return idagence;
	}
	public void setIdagence(Long idagence) {
		this.idagence = idagence;
	}
	public historiqueadmin() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
