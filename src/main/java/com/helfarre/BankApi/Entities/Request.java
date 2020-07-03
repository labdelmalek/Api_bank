package com.helfarre.BankApi.Entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "request")
public class Request implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	@Column(name = "idRequest")
	private Long idRequest;
	
	@Column(name = "dateRequest")
	private Date dateRequest;
	
	@OneToOne(cascade = CascadeType.DETACH, orphanRemoval = false)
	private banquier bq;
	
	//close_request attribute means the status of the request
	@Column(name = "closeRequest")
	private Boolean closeRequest;
	

	

	public Request() {
		
	}

	public Long getIdRequest() {
		return idRequest;
	}



	public void setIdRequest(Long idRequest) {
		this.idRequest = idRequest;
	}



	public Date getDateRequest() {
		return dateRequest;
	}



	public void setDateRequest(Date dateRequest) {
		this.dateRequest = dateRequest;
	}



	public Boolean getCloseRequest() {
		return closeRequest;
	}



	public void setCloseRequest(Boolean closeRequest) {
		this.closeRequest = closeRequest;
	}

	public banquier getBq() {
		return bq;
	}

	public void setBq(banquier bq) {
		this.bq = bq;
	}



}