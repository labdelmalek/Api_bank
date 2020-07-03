package com.helfarre.BankApi.Entities;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class RequestCC extends Request {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
   
    @ManyToOne()
    private compte_cheque cheque; 

  
    public RequestCC() {

    }
	public compte_cheque getCheque() {
		return cheque;
	}
	public void setCheque(compte_cheque cheque) {
		this.cheque = cheque;
	}





}