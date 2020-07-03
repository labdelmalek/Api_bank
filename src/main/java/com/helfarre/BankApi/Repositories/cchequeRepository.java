package com.helfarre.BankApi.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helfarre.BankApi.Entities.ccheque;
import com.helfarre.BankApi.Entities.compte;

public interface cchequeRepository extends JpaRepository<ccheque, Long> {
	List<ccheque>findAll();
	
	Optional<List<ccheque>>   findByCompte(compte cp) ;
}
