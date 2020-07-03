package com.helfarre.BankApi.Services;

import java.util.List;
import java.util.Optional;

import com.helfarre.BankApi.Entities.Agence;
import com.helfarre.BankApi.Entities.infocredit;

public interface infocreditservice {

	
	

	public infocredit findById(Long theId);
	
	public void addinfo(infocredit agence);


	public void deleteById(Long theId);
	

}
