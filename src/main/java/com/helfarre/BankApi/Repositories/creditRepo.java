package com.helfarre.BankApi.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helfarre.BankApi.Entities.compte;
import com.helfarre.BankApi.Entities.credit;

public interface creditRepo extends JpaRepository<credit, Long>{
	Optional<List<credit>> findByCredit(compte cp) ;
	List<credit> findAll();
	Optional<List<credit>> findByCreditClient_Id(Long id);
	Optional<List<credit>> findByCreditLagenceId(Long id);

}
