package com.helfarre.BankApi.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helfarre.BankApi.Entities.Client;
import com.helfarre.BankApi.Entities.RequestCartCredit;

import com.helfarre.BankApi.Entities.compte_cheque;

public interface RequestCartCreditRepository extends JpaRepository<RequestCartCredit, Long> {
	/*
	List<RequestCartCredit> findByClient(Client client);
	List<RequestCartCredit> findByBanker(banquier banker);
	*/
	List<RequestCartCredit> findByCheque(compte_cheque compteCheque);
	List<RequestCartCredit> findByCheque_Client(Client cl);
}
