package com.helfarre.BankApi.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.helfarre.BankApi.Entities.Client;
import com.helfarre.BankApi.Entities.compte_cheque;

public interface chequeRepository extends JpaRepository<compte_cheque, Long> {
	
	Optional<List<compte_cheque>> findByClient(Client client) ;
	List<compte_cheque> findByClient_Id(Long id) ;
	
	Optional<compte_cheque> findByNumcompte(String num_compte) ;
	
	Optional<compte_cheque> findByNuminternational(String numinternational) ;
	
	Optional<compte_cheque> findById(long id);
	
	List<compte_cheque>findAll();
	//String findByClient_Banquiers_Agence_Codeagence(String num);
	
	@Query(value="select numcompte from num ",nativeQuery=true)
	String getnumer();
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value="update num  n set  numcompte=n.numcompte+1",nativeQuery=true)
	void test();
	
}
