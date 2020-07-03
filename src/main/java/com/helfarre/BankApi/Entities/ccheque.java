package com.helfarre.BankApi.Entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name= "ccheque")
public class ccheque  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(name = "id", updatable = false, nullable = false)
    private Long id;
	
	@Column(name = "nb_pages", updatable = true, nullable = false)
	@Max(value=100)
	private int nb_pages;
	
	@Column(name = "frais_ch", updatable = true, nullable = false)
	private int frais_ch;
	@Column(name = "rib", updatable = true, nullable = false)
	private String rib;
	
	public String getRib() {
		return rib;
	}

	public void setRib(String rib) {
		this.rib = rib;
	}
	@JsonBackReference(value="carnet5")
	@ManyToOne
	private compte_cheque compte;
   
	

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public int getNb_pages() {
	return nb_pages;
}

public void setNb_pages(int nb_pages) {
	this.nb_pages = nb_pages;
}

public int getFrais_ch() {
	return frais_ch;
}

public void setFrais_ch(int frais_ch) {
	this.frais_ch = frais_ch;
}

public compte_cheque getCompte() {
    return compte;
}


public void setCompte(compte_cheque compte) {
	this.compte = compte;
}



public ccheque( int nb_pages, int frais_ch) {
	super();
	
	this.nb_pages = nb_pages;
	this.frais_ch = frais_ch;
}

public static long getSerialversionuid() {
	return serialVersionUID;
}

public ccheque() {}



}
