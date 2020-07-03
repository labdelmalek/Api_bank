package com.helfarre.BankApi.Entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="admin")

public class admin {

	

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
	
		

	
	@Column(name="email")
   // @NotNull(message = "email cannot be null.")
	//@Email(message = "Enter a valid email address.") 
	private String email;
 
	@Column(name="password")
	private String password;

	@Column(name="cin")
	//@NotNull(message = "CIN cannot be null.") 
	//@NotBlank()
	//@Size(min=7, max=15,message = "enter a valid CIN.")
	//@Pattern(regexp="^[a-zA-Z]{1,2}[0-9].*[0-9]$", message="enter a valid CIN ,check your national letter .")
	private String cin;
	
	


	@ManyToOne(cascade = CascadeType.DETACH, fetch= FetchType.EAGER)
	private Role role;




	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
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




	public String getPhone() {
		return phone;
	}




	public void setPhone(String phone) {
		this.phone = phone;
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




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}




	public String getPassword() {
		return password;
	}




	public void setPassword(String password) {
		this.password = password;
	}




	public String getCin() {
		return cin;
	}




	public void setCin(String cin) {
		this.cin = cin;
	}




	public Role getRole() {
		return role;
	}




	public void setRole(Role role) {
		this.role = role;
	}




	public static long getSerialversionuid() {
		return serialVersionUID;
	}




	public admin() {
		super();
		// TODO Auto-generated constructor stub
	}




	@Override
	public String toString() {
		return "admin [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", adresse=" + adresse
				+ ", phone=" + phone + ", dateNaissance=" + dateNaissance + ", joiningDate=" + joiningDate + ", email="
				+ email + ", password=" + password + ", cin=" + cin + ", role=" + role + "]";
	}
	
}
