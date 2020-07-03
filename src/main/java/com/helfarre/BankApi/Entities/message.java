/*package com.helfarre.BankApi.Entities;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;


@Entity
@Table(name="message")
@Inheritance(strategy = InheritanceType.JOINED)
public class message {
	
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_message")
	private Long id;
	
	@Column(name="country")
	@NotNull(message = "country cannot be null.") 
	@NotBlank(message = "country cannot be empty.")
	@Size(min=3, max=30,message = "enter a valid country name.")
	private String country;
	
	@Column(name="message")
	@NotNull(message = "message cannot be null.") 
	@NotBlank(message = "message cannot be empty.")
	@Size(min=10,message = "enter a valid message.")
	private String message;
	
	@Column(name="titre")
	@NotNull(message = "titre cannot be null.") 
	@NotBlank(message = "titre cannot be empty.")
	private String titre;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="reply")
	private Boolean reply;
	
	public Boolean isReply() {
		return reply;
	}

	public void setReply(Boolean reply) {
		this.reply = reply;
	}


	@ManyToOne
	@JoinColumn(name="id_client")
	
	private Client lesClients;
	
	@ManyToOne
	
	private Agence contacts;

	public Boolean getReply() {
		return reply;
	}

	public Agence getContacts() {
		return contacts;
	}

	public void setContacts(Agence contacts) {
		this.contacts = contacts;
	}

	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date2) {
		this.date = date2;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public message() {
		
	}

	

	public Client getLesClients() {
		return lesClients;
	}


	public void setLesClients(Client lesClients) {
		this.lesClients = lesClients;
	}

	public String getTitre() {
		return titre;
	}


	public void setTitre(String titre) {
		this.titre = titre;
	}

	
	


	
	
	
	
	
	
	
	
		
}


*/









