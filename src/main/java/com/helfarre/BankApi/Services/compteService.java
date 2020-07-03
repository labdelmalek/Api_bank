package com.helfarre.BankApi.Services;

import java.util.List;
import java.util.Optional;

import com.helfarre.BankApi.Entities.compte;

public interface compteService {
	Optional<compte> getCompteById(Long id) ;
	
	Optional<List<compte>> getCompteByClient(Long id) ;
	
	Optional<List<compte>> getCompteByAgenceId(Long id);

	compte saveOrUpdateCompte(compte compte) ;

	boolean retraitcompte(long id, float somme);
	
	boolean creditcompte(long id, float somme);

	Optional<compte> getCompteByNum(String accountnum);

	List<compte> findAll();

	void datedernier(long id);

	Optional<compte> getCompteByNuminternational(String accountnum);
	
	
}
