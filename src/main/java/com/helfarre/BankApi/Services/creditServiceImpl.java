package com.helfarre.BankApi.Services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.helfarre.BankApi.Entities.Transactionepaepa;
import com.helfarre.BankApi.Entities.compte;
import com.helfarre.BankApi.Entities.credit;
import com.helfarre.BankApi.Repositories.creditRepo;
@Service
public class creditServiceImpl implements creditService {
	@Autowired
	creditRepo creditrepo;
	@Autowired
	TransactionImpl transactionService;
	@Autowired
	compteService cpservice;
	

	@Override
	public Optional<credit> getCreditById(Long id) {
		 Optional<credit> cr=creditrepo.findById(id);
			

			return cr;
	}

	@Override
	public Optional<List<credit>> getCreditByCompte(compte cp) {
		Optional<List<credit>> test=creditrepo.findByCredit(cp);
		return test;
	}

	@Override
	public credit saveOrUpdateCredit(credit cr) {
		creditrepo.save(cr);		
		return cr;
	}
	@Override
    public Optional<List<credit>> getCreditByclient(Long idcl) {
        Optional<List<credit>> test=creditrepo.findByCreditClient_Id(idcl);




        return test;
    }
	@Override
	public Optional<credit> deleteCredit(Long id) {
		Optional<credit> cr=creditrepo.findById(id);
		creditrepo.deleteById(id);	
		return cr;
	}

	@Override
	public List<credit> findAll() {
		return creditrepo.findAll();
	}

	
	

	@Scheduled(cron = "0 0 4 1 * ?")
//	@Scheduled(cron = "20 53 20 * * ?")
	  public void executer() {
		credit test =null;
		for (int i = 0; i < findAll().size(); i++) {
			
			
		  test=  findAll().get(i);
		  if(test.getMoisrest()>0&& test.isStatut()==true) {
		  test.getCredit().setBalance(test.getCredit().getBalance()-test.getMensualite());
		  test.setMoisrest(test.getMoisrest()-1);
		  
		   cpservice.getCompteByNuminternational("agence"+test.getCredit().getLagence().getId()).get().setBalance(cpservice.getCompteByNuminternational("agence"+test.getCredit().getLagence().getId()).get().getBalance()+test.getMensualite());
		   cpservice.saveOrUpdateCompte( cpservice.getCompteByNuminternational("agence"+test.getCredit().getLagence().getId()).get());

	  
		  Transactionepaepa ttt=new Transactionepaepa();
			Date date = new Date();
			ttt.setDate(date);
			ttt.setSomme(test.getMensualite());
	        ttt.setTransactionStatus(2);
	        ttt.setReceiver(cpservice.getCompteByNuminternational("agence"+test.getCredit().getLagence().getId()).get());
	        ttt.setSender(test.getCredit());
	        ttt.setType("credit");
	        ttt.setPerson(null);
	        transactionService.saveTransaction(ttt);
		  
		  
		  
		 saveOrUpdateCredit(test);
		 }
		  
		
		}}
	
	
	@Override
	public float simulationcredit(int nbmois, int somme, float taux) {
		float test=(float) ((somme*(taux/1200))/(1-Math.pow(1+(taux/1200), -nbmois)));
		return test;
	}

}
