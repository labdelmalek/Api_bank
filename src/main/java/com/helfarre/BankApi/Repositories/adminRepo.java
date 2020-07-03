package com.helfarre.BankApi.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.helfarre.BankApi.Entities.admin;
import com.helfarre.BankApi.Entities.banquier;

public interface adminRepo  extends JpaRepository<admin, Long>{
Optional<admin> findByPhone(String phone);
	
	Optional<admin> findByCin(String cin);
	List<admin> findAll();
	Optional<admin> findByEmail(String email);
	@Query(value = "SELECT cin FROM admin ", nativeQuery = true)
	List<String> findCin();
	
	@Query(value = "SELECT email FROM admin ", nativeQuery = true)
	List<String> findEmail();
}
