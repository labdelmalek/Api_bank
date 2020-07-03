package com.helfarre.BankApi.Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="compte")
//@JsonIgnoreProperties("transactionS,transactionR")
public   class compte implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	@Column(name = "id", updatable = false, nullable = false)
    private long id;
	//@JsonManagedReference
	@ManyToOne(cascade=CascadeType.MERGE )
	@JsonBackReference(value="compteclient")
	private Client client;
	@OneToOne(cascade = CascadeType.DETACH, orphanRemoval = false)
	private banquier bq;
	
	public banquier getBq() {
		return bq;
	}

	public void setBq(banquier bq) {
		this.bq = bq;
	}


	@Column(name = "numcompte")
	//@Pattern(regexp="^[0-9]{16}$", message="enter a valid national number , it must be 16.")
	//@Length(min=16, max=16,message="nombre de chiffres du  numcompte est different de 16 chiffres ")
	private String numcompte;
	
	@Column(name = "numinternational")
	//@Length(min=24, max=24, message="numinternational different de 24 chiffres")
	//@Pattern(regexp="^[0-9]{24}$", message="enter a valid International number , it must be 24.") 
	private String numinternational;
	
	@Column(name = "balance", updatable = true, nullable = false)
	private double balance;
	
	@Column(name = "creation_date", updatable = true, nullable = false)
	//@Future(message="date creation  future")
	private java.util.Date  creation_date;
	
	
	@Column(name = "is_suspended", updatable = true, nullable = true)
	private Boolean is_suspended;
	
	@Column(name = "typecompte", updatable = true, nullable = true)
	private String typecompte;
	private String rib;

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

	public String getTypecompte() {
		return typecompte;
	}


	public void setTypecompte(String typecompte) {
		this.typecompte = typecompte;
	}

	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy="sender")
	//@JsonBackReference
	@JsonBackReference(value="transactions")
	//@JsonBackReference(value="seller")
	private Set<Transactionepaepa> transactionS;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy="receiver")
	//@JsonBackReference
	@JsonBackReference(value="transactionr")
	private Set<Transactionepaepa> transactionR;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,  mappedBy="compte", orphanRemoval=true)
	private Set<cheque> cheques;
	
	
	
	@JsonBackReference(value="compteeee")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,  mappedBy="credit", orphanRemoval=true)
	private Set<credit> credits;
	
	
	
	
	public Set<cheque> getCheques() {
		return cheques;
	}


	public void setCheques(Set<cheque> cheques) {
		this.cheques = cheques;
	}


	public Set<credit> getCredits() {
		return credits;
	}


	public void setCredits(Set<credit> credits) {
		this.credits = credits;
	}


	@ManyToOne
	@JsonBackReference(value="comptesagence")
	private Agence lagence;

		public Set<Transactionepaepa> getTransactionS() {
		return transactionS;
	}
	

public Agence getLagence() {
			return lagence;
		}


		public void setLagence(Agence lagence) {
			this.lagence = lagence;
		}


public void setTransactionS(Set<Transactionepaepa> transactionS) {
		this.transactionS = transactionS;
	}

	public Set<Transactionepaepa> getTransactionR() {
		return transactionR;
	}

	public void setTransactionR(Set<Transactionepaepa> transactionR) {
		this.transactionR = transactionR;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	

	public void setClient(Client client) {
		this.client = client;
	}

	public String getNumcompte() {
		return numcompte;
	}


	public Client getClient() {
		return client;
	}

	public void setNumcompte(String numcompte) {
		this.numcompte = numcompte;
	}

	public String getNuminternational() {
		return numinternational;
	}

	public void setNuminternational(String numinternational) {
		this.numinternational = numinternational;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public java.util.Date getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(Date date2) {
		this.creation_date = date2;
	}

	public Boolean getIs_suspended() {
		return is_suspended;
	}

	public void setIs_suspended(Boolean is_suspended) {
		this.is_suspended = is_suspended;
	}

	

	public compte() {
		
	}


	


	

	


}
