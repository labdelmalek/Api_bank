package com.helfarre.BankApi.Services;

import java.util.List;
import java.util.Optional;
import com.helfarre.BankApi.Entities.Transactionepaepa;



public interface TransactionService {
	
	
	List<Transactionepaepa> getAllTransactions();
	
	Optional<Transactionepaepa> getTransactionById(Long id);
	
    List<Transactionepaepa> getReceiverTransactions(Long receiverId) ;
	
	List<Transactionepaepa> getSenderTransactions(Long senderId) ;
	
	void saveTransaction(Transactionepaepa trans)  ;
	
	Optional<List<Transactionepaepa>> transactionsenderbyagence(Long id);

	Optional<List<Transactionepaepa>> transactionreceiverbyagence(Long id);
	Optional<List<Transactionepaepa>> transactionallbyagence(Long id);

	List<Transactionepaepa> getalltransactionsbyId(Long id);

	List<Transactionepaepa> getReceiverversement(Long senderId);

	List<Transactionepaepa> getSenderretrait(Long senderId);

	List<Transactionepaepa> transactionscartebancairebycompte(Long id);
	
	
	
}
