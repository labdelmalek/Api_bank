package com.helfarre.BankApi.Services;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.helfarre.BankApi.Entities.Transactionepaepa;
import com.helfarre.BankApi.Entities.cheque;
import com.helfarre.BankApi.Entities.compte_cheque;
import com.helfarre.BankApi.Repositories.chequeRepos;
import com.helfarre.BankApi.Repositories.chequeRepository;
@Service
public class chequesServiceImpl implements chequesService {
	@Autowired
	private chequeRepos chequeRepo;
	@Autowired
	private chequeRepository chequeRepos;
	@Autowired
	private chequeService chequeserv;
	@Autowired
	private TransactionImpl transactionService;

	@Override
	public Optional<cheque> getChequeById(long id) {
		 Optional<cheque> cheque=chequeRepo.findById(id);
			

			return cheque;
	
	}


	@Override
	public Optional<List<cheque>> getChequeBycomptesender(String numinternational) {
		Optional<List<cheque>>test=chequeRepo.findByNuminternational(numinternational);
		return test;
	}
	@Override
	public Optional<List<cheque>> getChequeBycomptereceiver(String benificiaire) {
	
		Optional<List<cheque>>test=chequeRepo.findByBenificiaire(benificiaire);
		return test;
	}

	@Override
	public Optional<List<cheque>> getallchequecompte(String  numinternational ) {
		Optional<List<cheque>>test=chequeRepo.findByNuminternational(numinternational);
		test.get().addAll(chequeRepo.findByBenificiaire(numinternational).get());
		return test;
	}

	

	@Override
	public void saveOrUpdateCheque(cheque ch) {
		chequeRepo.save(ch);
		
	}

	@Override
	public List<cheque> findAll() {
		return chequeRepo.findAll();
	}

	@Override
	public boolean encaisserchequeespeces(cheque ch) {
		Optional<compte_cheque> cmp=chequeRepos.findByNuminternational(ch.getNuminternational());
		if(cmp.isPresent()&&cmp.get().getBalance()>=ch.getMontant()&&cmp.get().getIs_suspended()==false) {
			cmp.get().setBalance(cmp.get().getBalance()-ch.getMontant());
			ch.setStatus(2);		
			saveOrUpdateCheque(ch);
			chequeserv.saveOrUpdateCompte_cheque(cmp.get());
			
			return true;}
		return false;
	}



	@Override
	public boolean encaisserchequebarrébynuminternational(cheque ch) {
		if(ch.getTypeencaissement().equals("compte")==true) {
		Optional<compte_cheque> ret=chequeRepos.findByNuminternational(ch.getNuminternational());
		System.out.println("test2");
		Optional<compte_cheque> ben=chequeRepos.findByNuminternational(ch.getBenificiaire());
		
		
		if(ret.isPresent()&&ben.isPresent()&&ben.get().getIs_suspended()==false&&ret.get().getBalance()>=ch.getMontant()&&ret.get().getIs_suspended()==false) {
			ret.get().setBalance(ret.get().getBalance()-ch.getMontant());
			System.out.println("test3");
			ben.get().setBalance(ben.get().getBalance()+ch.getMontant()-1);
			
			saveOrUpdateCheque(ch);
			chequeserv.saveOrUpdateCompte_cheque(ret.get());
			chequeserv.saveOrUpdateCompte_cheque(ben.get());
			return true;}
		}
		return false;
	}

	@Scheduled(cron = "0 0 4 * * ?")
	
	  public void executer() {
		cheque test =null;
		for (int i = 0; i < findAll().size(); i++) {
			
			
			
		  test=  findAll().get(i);
		  
		  if(test.getStatus()==0  && test.getTypeencaissement().equals("compte")==true) {
		
			 if( encaisserchequebarrébynuminternational(test)==true) {
				 

					Transactionepaepa ttt=new Transactionepaepa();
					Date date = new Date();
					ttt.setDate(date);
					ttt.setSomme(test.getMontant());
			        ttt.setTransactionStatus(2);
			        ttt.setReceiver(chequeRepos.findByNuminternational(test.getBenificiaire()).get());
			        ttt.setSender(test.getCompte());
			        ttt.setType("encaissementcompte");
			        ttt.setPerson(null);
			        transactionService.saveTransaction(ttt);
					
					
				
				 test.setStatus(2);
			 }
			 else  {
				
				 test.setStatus(1);
			 }
			
		  }
		 
		  saveOrUpdateCheque(test);
		
		}
		
		
	  }

}
