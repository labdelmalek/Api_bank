package com.helfarre.BankApi.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.helfarre.BankApi.Entities.Client;





public interface ClientJpaRepository extends JpaRepository<Client,Long> {

	
	Optional<Client> findByEmail(String email);
	Optional<Client> findByPhone(String email);
	// Find by CIN
	
	Optional<Client> findByCin(String cin);
	
	
	
	@Query(value = "SELECT cin FROM client ", nativeQuery = true)
	List<String> findCin();
	
	@Query(value = "SELECT email FROM client ", nativeQuery = true)
	List<String> findEmail();
	
	
}
