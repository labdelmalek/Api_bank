package com.helfarre.BankApi.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.helfarre.BankApi.Entities.cbancaire;
import com.helfarre.BankApi.Entities.compte;


public interface cbancaireRepository extends JpaRepository<cbancaire, Long> {
	
	List<cbancaire>findAll();
	
	Optional<cbancaire>   findByNumcarte(String numcarte) ;
	
	Optional<List<cbancaire>>   findByCompte(compte cp) ;
	
	 //void saveOrUpdateCbancaires(List<cbancaire> cheque);

	@Query(value="select numcarte from num",nativeQuery=true)
	String getnumer();
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value="update num  n set  numcarte=n.numcarte+1",nativeQuery=true)
	void test();
}
