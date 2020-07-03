package com.helfarre.BankApi.Entities;


import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
		@Entity
		@Table(name="c_cheque")
		@PrimaryKeyJoinColumn(name = "compte_id")
		public class compte_cheque extends compte {
		
		private static final long serialVersionUID = 1L;
		
		private float frais;
		@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,  mappedBy="compte", orphanRemoval=true)
		private Set<ccheque> carnet;
		
		@Column(name = "dernier_frais", updatable = true, nullable = false)
		private java.util.Date dernier_frais;
		//@JsonManagedReference(value="carte")
		
		@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,  mappedBy="compte", orphanRemoval=true)
		private Set<cbancaire> carte;
		
	    @JsonBackReference(value="test")
		@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,  mappedBy="cheque", orphanRemoval=true)
		private Set<RequestCartCredit> cartecredit;
		public Set<RequestCartCredit> getCartecredit() {
			return cartecredit;
		}

		public void setCartecredit(Set<RequestCartCredit> cartecredit) {
			this.cartecredit = cartecredit;
		}

		public Set<RequestCC> getCarnetcheque() {
			return carnetcheque;
		}

		public void setCarnetcheque(Set<RequestCC> carnetcheque) {
			this.carnetcheque = carnetcheque;
		}
	    @JsonBackReference(value="carnet")

		 @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,  mappedBy="cheque", orphanRemoval=true)
		private Set<RequestCC> carnetcheque;
		


		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		
		public Set<cbancaire> getCarte() {
			return carte;
		}

		@Override
		public String toString() {
			return "compte_cheque [frais=" + frais + ", dernier_frais=" + dernier_frais + "]";
		}
		
		public compte_cheque() {
		
		}
		
		
		
		public Set<ccheque> getCarnet() {
			return carnet;
		}

		public void setCarnet(Set<ccheque> carnet) {
			this.carnet = carnet;
		}

		public void setCarte(Set<cbancaire> carte) {
			this.carte = carte;
		}
		
		public float getFrais() {
			return frais;
		}
		
		public void setFrais(float frais) {
			this.frais = frais;
		}
		
		public java.util.Date getDernier_frais() {
			return dernier_frais;
		}
		
		public void setDernier_frais(java.util.Date date) {
			this.dernier_frais = date;
		}


}
