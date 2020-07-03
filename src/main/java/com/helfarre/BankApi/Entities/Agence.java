package com.helfarre.BankApi.Entities;

import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="agency")
public class Agence  {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
	
	@Column(name="name")
	//@NotNull(message = "name cannot be null.") 
	//@NotBlank(message = "name cannot be null.")
	private String name;
	
	@Column(name="country")
	//@NotNull(message = "country cannot be null.") 
	//@NotBlank(message = "country cannot be null.")
	private String country;
	
	@Column(name="adresse")
	//@NotNull(message = "adresse cannot be null.") 
	//@NotBlank(message = "adresse cannot be null.")
	private String adresse;
	
	@Column(name="email")
	//@NotNull(message = "email cannot be null.")
	//@NotBlank(message = "email cannot be null.")
	@Email(message = "Enter a valid email address.") 
	private String email;
	
	@Column(name="phone")
	//@NotNull(message = "phone cannot be null.") 
	//@NotBlank(message = "phone cannot be null.")
	@Size(min=9, max=30,message = "Phone must contains at least 9 characters.")
	private String phone;
	
	@Column(name="fax")
	//@NotNull(message = "fax cannot be null.") 
    //@NotBlank(message = "fax cannot be null.")
	@Size(min=5, max=30,message = "Fax must contains at least 9 characters.")
	private String fax;
	

	@Column(name="numagence")
	@Size(min=8, max=8,message = "num�ro d'agence must contains at least 8 characters.")
	private String numagence;
/*
	//@JsonManagedReference(value="messageagence")
	@OneToMany(mappedBy="contacts",fetch = FetchType.LAZY ,cascade = CascadeType.ALL,orphanRemoval = true)
	@JsonBackReference(value="message")
	private List<message> message;
    
	public List<message> getMessage() {
		return message;
	}


	public void setMessage(List<message> message) {
		this.message = message;
	}
*/

	@JsonManagedReference(value="banker")
	@OneToMany(mappedBy="agence",fetch = FetchType.LAZY ,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<banquier> banqs;
   
    
    @OneToMany(mappedBy = "lagence", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference(value="comptesagence")
	private List<compte> clis;
    
    
    
    public Agence() {
    	
    }

    
    public String getNumagence() {
		return numagence;
	}

	public void setNumagence(String numagence) {
		this.numagence = numagence;
	}
	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public List<banquier> getBanqs() {
		return banqs;
	}

	public void setBanqs(List<banquier> banqs) {
		this.banqs = banqs;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

	@Override
	public String toString() {
		return "Agence [id=" + id + ", name=" + name + ", country=" + country + ", adresse=" + adresse + ", email="
				+ email + ", phone=" + phone + ", fax=" + fax + ", banqs=" + banqs + "]";
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}


	public List<compte> getClis() {
		return clis;
	}


	public void setClis(List<compte> clis) {
		this.clis = clis;
	}

	


	


	

	
	

	


	
	

	

	
	
	


	
		
}











