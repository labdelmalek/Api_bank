package com.helfarre.BankApi.Services;

import java.util.List;
import java.util.Optional;


import com.helfarre.BankApi.Entities.cbancaire;
import com.helfarre.BankApi.Entities.compte_cheque;

public interface cbancaireService {
	List<cbancaire> getAllCbancaire() ;
	Optional<cbancaire> getCbancaireById(Long id) ;
	cbancaire saveOrUpdateCbancaire(cbancaire carte) ;
	Optional<cbancaire> deleteCbancaire(Long id);
	Optional<cbancaire> getCbancaireByNumcarte(String num) ;
	Optional<List<cbancaire>> getCbancaireByCompte(compte_cheque compte) ;
/*	@Query("SELECT u FROM cbancaire u WHERE u.num = numcarte")
	Optional<cbancaire> testCbancaire(String numcarte);*/


}
