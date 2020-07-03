package com.helfarre.BankApi.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helfarre.BankApi.Entities.compte;

public interface compteRepository extends JpaRepository<compte, Long> {
	
	Optional<List<compte>> findByClient_Id(Long id);
	
	//Optional<List<compte>> findByClient_Lagence_Id(Long id);
	

	Optional<compte> findByNumcompte(String numC);
	Optional<compte> findByNuminternational(String numC);
	
	List<compte>findAll();
	Optional<List<compte>> findByLagence_Id(Long idagence);
	
}
