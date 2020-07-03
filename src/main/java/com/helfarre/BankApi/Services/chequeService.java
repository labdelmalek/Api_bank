package com.helfarre.BankApi.Services;

import java.util.List;
import java.util.Optional;

import com.helfarre.BankApi.Entities.Client;
import com.helfarre.BankApi.Entities.cbancaire;
import com.helfarre.BankApi.Entities.compte_cheque;

public interface chequeService {
	Optional<compte_cheque> getCompte_chequeById(long id) ;
	Optional<List<compte_cheque>> getCompte_chequeByClient(Client client) ;

	compte_cheque saveOrUpdateCompte_cheque(compte_cheque cheque) ;
	
	List<compte_cheque> findAll();
	Optional<compte_cheque> getCompte_chequeByNum_compte(String num_compte) ;
	
	boolean verifconditioncarte(compte_cheque compte);
	Optional<compte_cheque> getCompte_chequeByNuminternational(String  numinternational) ;
	//boolean retraitcompte(long id,float somme);
	//boolean creditcompte(long id,float somme);
	boolean verificonditioncarte(compte_cheque compte, cbancaire carte);
	boolean retraitcarte(long id, int somme, cbancaire carte);
	int getreste(String compte);
}
