package com.helfarre.BankApi.Entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;

import javax.persistence.InheritanceType;


@Entity
@Table(name="person")
@Inheritance(strategy = InheritanceType.JOINED)
public class person {
	
	@Override
	public String toString() {
		return "person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", adresse=" + adresse
				+ ", phone=" + phone + ", dateNaissance=" + dateNaissance + ", joiningDate=" + joiningDate + "]";
	}



	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="fname")
	//@NotNull(message = "firstname cannot be null.") 
	//@NotBlank(message = "firstname cannot be null.")
	//@Size(min=3, max=30,message = "enter a valid firstName.")
	private String firstName;
	
	@Column(name="lname")
//	@NotNull(message = "lastname cannot be null.") 
//	@NotBlank(message = "lastname cannot be null.")
//	@Size(min=3, max=30,message = "enter a valid lastName.")
	private String lastName;
	
	
	@Column(name="adresse")
//	@NotNull(message = "adress cannot be null.")
//	@NotBlank(message = "adress cannot be null.")
//	@Size(min=8, max=160,message = "enter a valid adress.")
	private String adresse;
	

	

	@Column(name="phone")
//	@NotNull(message = "phone cannot be null.")
//	@NotBlank(message = "phone cannot be null.")
//	@Size(min=9, max=30,message = "Phone must contains at least 9 characters.")
	private String phone;
	
	@Column(name="date_naissance")
//	@NotNull(message = "Birth day cannot be null.") 
//	@NotBlank(message = "birth day cannot be null.")
	private Date dateNaissance;
	
	@Column(name="joining_date")
//	@NotNull(message = "joining date cannot be null.") 
//	@NotBlank(message = "joining date cannot be null.")
	private Date joiningDate;
	
		
	public person() {
		
	}


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}




	

	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}
		
}











