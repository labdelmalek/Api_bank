package com.helfarre.BankApi.Services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helfarre.BankApi.Entities.typecarte;
import com.helfarre.BankApi.Repositories.typecarteRepository;
@Service
public class typecarteServiceImpl implements typecarteService {
	 @Autowired
	 private typecarteRepository typerepo;
	@Override
	public typecarte gettypecarteByNom(String nom) {
typecarte type=typerepo.findByNom(nom);	
return type;
	}

}
