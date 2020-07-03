package com.helfarre.BankApi.Entities;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity

public class RequestCartCredit extends Request{

	

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String CardType;
    @ManyToOne  
    private compte_cheque cheque;

    public compte_cheque getCheque() {
		return cheque;
	}

	public void setCheque(compte_cheque cheque) {
		this.cheque = cheque;
	}

	

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        CardType = cardType;
    }

    public RequestCartCredit() {

    }
}
	

