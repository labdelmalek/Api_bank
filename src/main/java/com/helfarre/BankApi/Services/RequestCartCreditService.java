package com.helfarre.BankApi.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.helfarre.BankApi.Entities.Client;
import com.helfarre.BankApi.Entities.RequestCartCredit;
import com.helfarre.BankApi.Entities.compte_cheque;

@Service
public interface RequestCartCreditService {
	
	
	RequestCartCredit saveRequestCartCredit(RequestCartCredit requestCartCredit);
	RequestCartCredit updateRequestCartCredit(RequestCartCredit requestCartCredit);
	Optional<RequestCartCredit> deleteRequestCartCredit(Long idRequest);
	public boolean verificationCloseRequest(RequestCartCredit req);
	Optional<RequestCartCredit> getRequestCartCreditById(Long idRequest);
	List<RequestCartCredit> getRequestCartCreditByCompteCheque(compte_cheque compteCheque);
	List<RequestCartCredit> findAll();
	public List<RequestCartCredit> getRequestByClient(Client cl);
}
