package com.helfarre.BankApi.Services;

import java.util.List;
import java.util.Optional;

import com.helfarre.BankApi.Entities.compte;
import com.helfarre.BankApi.Entities.credit;

public interface creditService {
	Optional<credit> getCreditById(Long id) ;
	Optional<List<credit>> getCreditByCompte(compte cp) ;

	credit saveOrUpdateCredit(credit cr) ;
	Optional<credit> deleteCredit(Long id);
	List<credit> findAll();
	float simulationcredit(int nbmois,int somme,float taux);
	public Optional<List<credit>> getCreditByclient(Long idcl);
	
}
