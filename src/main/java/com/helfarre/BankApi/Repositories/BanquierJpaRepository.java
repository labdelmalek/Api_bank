package com.helfarre.BankApi.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.helfarre.BankApi.Entities.banquier;


public interface BanquierJpaRepository extends JpaRepository<banquier,Long> {
	
	Optional<banquier> findById(Long id);
	List<banquier> findByAgence_Id(Long agencyId);
	
	Optional<banquier> findByPhone(String phone);
	
	Optional<banquier> findByCin(String cin);
	
	Optional<banquier> findByEmail(String email);
	@Query(value = "SELECT cin FROM banquier ", nativeQuery = true)
	List<String> findCin();
	
	@Query(value = "SELECT email FROM banquier ", nativeQuery = true)
	List<String> findEmail();
	
	
	
}
