package com.helfarre.BankApi.Services;

import java.util.List;
import java.util.Optional;

import com.helfarre.BankApi.Entities.compte_epargne;

public interface epargneService {
	
	
	Optional<List<compte_epargne>> findByClient(Long id);
	
	Optional<List<compte_epargne>> findByClient_agence_Id(Long id);
	
	Optional<compte_epargne> getCompte_epargneById(long id);

	compte_epargne saveOrUpdateCompte_epargne(compte_epargne epargne) ;
	
	
	
	List<compte_epargne> findAll();

	Optional<compte_epargne> getCompte_epargneByNum_compte(String num_compte) ;
	
	Optional<compte_epargne> getCompte_epargneByNuminternational(String numinternational) ;
	
	//boolean creditcompte(long id, float somme);
	
	//boolean retraitcompte(long id, float somme);
	
	//void datedernier(long id);
}
