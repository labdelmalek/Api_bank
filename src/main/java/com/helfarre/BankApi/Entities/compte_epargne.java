package com.helfarre.BankApi.Entities;

import java.util.Date;


import javax.persistence.Entity;

import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

		

		@Entity
		@Table(name="c_epargne")
		@PrimaryKeyJoinColumn(name = "compte_id")
		public class compte_epargne extends compte {
		
		private static final long serialVersionUID = 1L;
		
		private  float  taux;
		
		private Date dernier_interet;
		
		public float getTaux() {
			return taux;
		}
		
		public void setTaux(float taux) {
			this.taux = taux;
		}
		
		public Date getDernier_interet() {
			return dernier_interet;
		}
		
		public void setDernier_interet(Date dernier_interet) {
			this.dernier_interet = dernier_interet;
		}
		
		public compte_epargne( Date dernier_interet) {
			super();
			
			this.dernier_interet = dernier_interet;
		}
		
		public compte_epargne() {
			
		}
		


}
