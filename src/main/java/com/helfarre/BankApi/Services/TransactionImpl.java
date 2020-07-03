package com.helfarre.BankApi.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import com.helfarre.BankApi.Entities.Transactionepaepa;
import com.helfarre.BankApi.Entities.compte;
import com.helfarre.BankApi.Exceptions.ProduitIntrouvableException;
import com.helfarre.BankApi.Repositories.TransactionRespository;

@Service
public class TransactionImpl implements TransactionService {
	@Autowired
	private TransactionRespository transrespo;
	@Autowired
	private compteService epargneservice;
	
	

	@Override
	public List<Transactionepaepa> getAllTransactions() {
		return transrespo.findAll();
	}

	@Override
	public Optional<Transactionepaepa> getTransactionById(Long id) throws ProduitIntrouvableException{
		Optional<Transactionepaepa> trans=transrespo.findById(id);
		if(trans.isPresent()) {
		return  trans;
		}
		else throw new ProduitIntrouvableException("id incorrect pour transaction");
	
	}

	@Override
	public void saveTransaction(Transactionepaepa trans) {
		transrespo.save(trans);
	
		}
		

	


	@Scheduled(cron = "0 0 18 * * ?")
	  public void executer() {
		Transactionepaepa test =null;
		compte ttt=null;
		for (int i = 0; i < getAllTransactions().size(); i++) {
			
			
		  test=getAllTransactions().get(i);
		  float somme=test.getSomme();
		 
		  
		  if(test.getTransactionStatus()==1&&test.getType()=="transaction") {
		  ttt=test.getSender();
		  ttt.setBalance(ttt.getBalance()+somme);
		  test.setTransactionStatus(0);
		  saveTransaction(test);
		  epargneservice.saveOrUpdateCompte(ttt);
		}}
	}

	@Override
	public List<Transactionepaepa> getReceiverTransactions(Long receiverId) {
		
		return transrespo.findByReceiverIdAndType(receiverId, "transaction");
	}

	@Override
	public List<Transactionepaepa> getSenderTransactions(Long senderId) {
		
		return transrespo.findBySenderIdAndType(senderId, "transaction");
	}
	
	
	@Override
	public List<Transactionepaepa> getSenderretrait(Long senderId) {
		
		return transrespo.findBySenderIdAndType(senderId, "retrait");
	}

	@Override
	public List<Transactionepaepa> getReceiverversement(Long senderId) {
		
		return transrespo.findByReceiverIdAndType(senderId, "versement");
	}
	@Override
	public List<Transactionepaepa> getalltransactionsbyId(Long id){
		List<Transactionepaepa>test= transrespo.findByReceiverId(id);
		test.addAll(transrespo.findBySenderId(id));
		return test;
	}
	
	

	@Override
	public Optional<List<Transactionepaepa>> transactionsenderbyagence(Long id) {
Optional<List<Transactionepaepa>>test=transrespo.findBySenderLagence(id);
		return test;
	}

	@Override
	public Optional<List<Transactionepaepa>> transactionreceiverbyagence(Long id) {
		Optional<List<Transactionepaepa>>test=transrespo.findByReceiverLagence(id);
		return test;
	}

	@Override
	public Optional<List<Transactionepaepa>> transactionallbyagence(Long id) {
		Optional<List<Transactionepaepa>>test=transrespo.findByReceiverLagence(id);	
		test.get().addAll(transrespo.findBySenderLagence(id).get());
		return test;
	}
	@Override
	public List<Transactionepaepa> transactionscartebancairebycompte(Long id) {
		return transrespo.findBySenderIdAndType(id, "retraitcarte");	
		
		
	}	
}
