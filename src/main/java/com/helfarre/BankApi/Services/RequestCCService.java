package com.helfarre.BankApi.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.helfarre.BankApi.Entities.Client;
import com.helfarre.BankApi.Entities.RequestCC;

import com.helfarre.BankApi.Entities.compte_cheque;

@Service
public interface RequestCCService {

	RequestCC saveRequestCC(RequestCC requestCC);
	RequestCC updateRequestCC(RequestCC requestCC);
	Optional<RequestCC> deleteRequestCC(Long idRequest);

	Optional<RequestCC> getRequestCCById(Long idRequest);
	List<RequestCC> gerRequestByClient(Client cl);

	List<RequestCC> findAll();
	List<RequestCC> getRequestCCByCompteCheque(compte_cheque compteCheque);

	
}
