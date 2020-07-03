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
@Entity
@Table(name="transaction")
public class Transactionepaepa implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	@Id@Column(name="id_trans", unique = true, nullable = false)
	@GeneratedValue(strategy =GenerationType.AUTO)
	private long id_transaction;
	

	
	@ManyToOne
	//@JsonManagedReference
	private compte sender;
	
	@ManyToOne
	
	//@JsonManagedReference
	private compte receiver;
	
	@OneToOne(cascade = CascadeType.DETACH, orphanRemoval = false)
	private banquier bq;
	
	public banquier getBq() {
		return bq;
	}



	public void setBq(banquier bq) {
		this.bq = bq;
	}



	@Column(name="typetransaction" ,insertable = true, updatable = true, nullable = false)
	private String type;

	@Column(name="personne")
	private String person;
	


	@Column(name="somme")
	private float somme;
	
	
	@Column(name="date")
	private Date date;
	
	
	@Column(name="trans_status")
	private long  transactionStatus;
	

	public String getPerson() {
		return person;
	}



	public void setPerson(String person) {
		this.person = person;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}

	
    public Transactionepaepa() {
    	
    }



	public long getId_transaction() {
		return id_transaction;
	}



	public void setId_transaction(long id_transaction) {
		this.id_transaction = id_transaction;
	}



	public compte getSender() {
		return sender;
	}



	public void setSender(compte sender) {
		this.sender = sender;
	}



	public compte getReceiver() {
		return receiver;
	}



	public void setReceiver(compte receiver) {
		this.receiver = receiver;
	}



	public float getSomme() {
		return somme;
	}



	public void setSomme(float somme) {
		this.somme = somme;
	}



	public Date getDate() {
		return date;
	}



	public void setDate(Date date) {
		this.date = date;
	}



	public long getTransactionStatus() {
		return transactionStatus;
	}



	public void setTransactionStatus(long transactionStatus) {
		this.transactionStatus = transactionStatus;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
	
	
}
