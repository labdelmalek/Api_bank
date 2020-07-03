package com.helfarre.BankApi.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.helfarre.BankApi.Entities.cheque;
import com.helfarre.BankApi.Entities.compte_cheque;

public interface chequeRepos extends JpaRepository<cheque, Long>{


	//Optional<List<cheque>> findByComptechequeCompteId(long  idcompte) ;
	Optional<List<cheque>> findByBenificiaire(String test);
	Optional<List<cheque>> findByCompte(compte_cheque cp);
	Optional<List<cheque>> findByNuminternational(String cp);
	//Optional<List<cheque>> findByRib(String  rib) ;
	//Optional<List<cheque>> findByComptechequeCompteNuminternational(String  idcompte) ;
	Optional<cheque> findById(long id);
	
	List<cheque>findAll();
	
	
	
	
}
