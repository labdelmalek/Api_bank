package com.helfarre.BankApi.Services;

import java.util.List;
import java.util.Optional;
import com.helfarre.BankApi.Entities.ccheque;
import com.helfarre.BankApi.Entities.compte_cheque;

public interface cchequeService {
	List<ccheque> getAllCcheque() ;
	Optional<ccheque> getCchequeById(Long id) ;
	ccheque saveOrUpdateCcheque(ccheque cheque) ;
	//sOptional<ccheque> deleteCcheque(Long id);
	Optional<List<ccheque>> getCchequeByCompte(compte_cheque compte) ;
	//double getreste(String compte);
}
