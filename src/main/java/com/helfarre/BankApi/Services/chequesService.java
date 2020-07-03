package com.helfarre.BankApi.Services;

import java.util.List;
import java.util.Optional;


import com.helfarre.BankApi.Entities.cheque;


public interface chequesService {
	Optional<cheque> getChequeById(long id);
	
	

	void saveOrUpdateCheque(cheque ch) ;
	
	List<cheque> findAll();
	boolean encaisserchequeespeces(cheque ch);
	boolean encaisserchequebarrébynuminternational(cheque ch);


Optional<List<cheque>> getChequeBycomptesender(String numinternational);


 Optional<List<cheque>> getChequeBycomptereceiver(String numinternational);


 Optional<List<cheque>> getallchequecompte(String  numinternational );


	//Optional<List<cheque>> getChequeByrib(String rib);
	
	
	
}
