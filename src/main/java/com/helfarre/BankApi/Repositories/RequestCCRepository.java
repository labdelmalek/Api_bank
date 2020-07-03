package com.helfarre.BankApi.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helfarre.BankApi.Entities.Client;
import com.helfarre.BankApi.Entities.RequestCC;

import com.helfarre.BankApi.Entities.compte_cheque;

public interface RequestCCRepository extends JpaRepository<RequestCC, Long> {
	
	
	List<RequestCC> findByCheque_Client(Client client);
	/*
	List<RequestCC> findByBanker(banquier banker);*/
	
	List<RequestCC> findByCheque(compte_cheque compteCheque);

}
