package com.helfarre.BankApi.Services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.helfarre.BankApi.Entities.Client;
import com.helfarre.BankApi.Entities.Transactionepaepa;
import com.helfarre.BankApi.Entities.cbancaire;
import com.helfarre.BankApi.Entities.compte_cheque;
import com.helfarre.BankApi.Entities.typecarte;
import com.helfarre.BankApi.Repositories.chequeRepository;
import com.helfarre.BankApi.Repositories.typecarteRepository;
@Service

public class chequeServiceImpl implements chequeService{
@Autowired
private chequeRepository chequeRepo;

@Autowired
private typecarteRepository carteRepo;
@Autowired
private TransactionImpl transactionService;
@Autowired
private compteService cpservice;
	@Override
	public Optional<List<compte_cheque>> getCompte_chequeByClient(Client client) {
		Optional<List<compte_cheque>> cheque=chequeRepo.findByClient(client);
		return cheque;
	}


	@Scheduled(cron = "0 0 4 1 * ?")
	
	  public void executer() {
		}



	@Override
	public List<compte_cheque> findAll() {
		List<compte_cheque>test =chequeRepo.findAll();
		return test;

	}
	
	@Override
	public Optional<compte_cheque> getCompte_chequeById(long id) {
    Optional<compte_cheque> cheque=chequeRepo.findById(id);
		

		return cheque;
	}

	@Override
	public compte_cheque saveOrUpdateCompte_cheque(compte_cheque cheque) {
		chequeRepo.save(cheque);		
		return cheque;
	}

	

	@Override
	public Optional<compte_cheque> getCompte_chequeByNum_compte(String num_compte) {
		Optional<compte_cheque> cheque=chequeRepo.findByNumcompte(num_compte);
		return cheque;
	}








	@Override
	public Optional<compte_cheque> getCompte_chequeByNuminternational(String numinternational) {
		Optional<compte_cheque> cheque=chequeRepo.findByNuminternational(numinternational);
		return cheque;
	}





	@Override
	public boolean verifconditioncarte(compte_cheque compte) {
	//	String test=carteRepo.findByNom(nom)
		if(compte.getIs_suspended()==false&&compte.getBalance()>50) {
			return true;
			
		}
		return false;
	}



	@Override
	public boolean verificonditioncarte(compte_cheque compte,cbancaire carte) {
		float test=carteRepo.findByNom(carte.getNom_carte()).getPrix_carte();
		if(compte.getIs_suspended()==false&&compte.getBalance()>test) {
			compte.setBalance(compte.getBalance()-test);
			saveOrUpdateCompte_cheque(compte);
			
			
			
			

			Transactionepaepa ttt=new Transactionepaepa();
			Date date = new Date();
			ttt.setDate(date);
			ttt.setSomme(test);
	        ttt.setTransactionStatus(2);
	        ttt.setReceiver(cpservice.getCompteByNuminternational("agence"+compte.getLagence().getId()).get());
	        ttt.setSender(compte);
	        ttt.setType("fraiscarte");
	        ttt.setPerson(null);
	        transactionService.saveTransaction(ttt);
	        

			   cpservice.getCompteByNuminternational("agence"+compte.getLagence().getId()).get().setBalance(cpservice.getCompteByNuminternational("agence"+compte.getLagence().getId()).get().getBalance()+test);
			   cpservice.saveOrUpdateCompte( cpservice.getCompteByNuminternational("agence"+compte.getLagence().getId()).get());
			   
			
			
			
			return true;
		}
		return false;
	}

/*	@Override
	public boolean retraitcompte(long id, float somme) {
		compte_cheque cheque=chequeRepo.findById(id);
		if(cheque.getIs_suspended()==false&&cheque.getBalance()>somme) {
		cheque.setBalance(cheque.getBalance()-somme);
		saveOrUpdateCompte_cheque(cheque);
		return true;
		}return false;
	}
*/

	@Override
	public boolean retraitcarte(long id, int somme,cbancaire carte) {
		
		typecarte test =carteRepo.findByNom(carte.getNom_carte());
		
		float test2=test.getPlafond_retrait();
		
		Optional<compte_cheque> cheque=chequeRepo.findById(id);
		if(cheque.get().getBalance()>somme&&(carte.getRetraitjour()+somme)<=test2) {
		cheque.get().setBalance(cheque.get().getBalance()-somme);
		carte.setRetraitjour(carte.getRetraitjour()+somme);
		saveOrUpdateCompte_cheque(cheque.get());
		
		return true;
		}return false;
	}


/*	@Override
	public boolean creditcompte(long id, float somme) {
		compte_cheque cheque=chequeRepo.findById(id);
		if(cheque.getIs_suspended()==false) {
		cheque.setBalance(cheque.getBalance()+somme-1);
		saveOrUpdateCompte_cheque(cheque);
		return true;}return false;
	}

*/
	@Override
	public int getreste(String compte) {
		String codeagence="",codebanque="",numcompte="";
		int i=0;
		System.out.println(compte);
		while(compte.length()>i) {
			if(i<4) {
				codebanque=codebanque+compte.charAt(i);
			}
			
			if(i>=4&&i<8) {
				codeagence=codeagence+compte.charAt(i);
			}
			
			if(i>=8&&i<24) {
				numcompte=numcompte+compte.charAt(i);
			}
			i++;
			
		}
		double test= 97 - ( 89*Double.parseDouble(codebanque)+15*Double.parseDouble(codeagence)+3*Double.parseDouble(numcompte));
		double vari=test%97;
				if(vari<97&&vari>=0)return (int)(vari); 
				if(vari<97&&vari<0)return (int)(vari+97); 

		return 10000;
	}
	@Scheduled(cron = "36 41 10 * * ?")
	 public void executer1() throws ParseException {

		 Date todayDate=new Date();


		 compte_cheque cc=null;
		 //for(compte_cheque c: findAll()) { 

		 for(int c=0;c<findAll().size();c++) {

			 cc=chequeRepo.findAll().get(c);

			 Date da=addYear(cc.getDernier_frais(),1);
			 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
			 String date1= formatter.format(todayDate);
			 String date2=formatter.format(da);


			 System.out.println(c+" "+date2+"  "+date1);
			 if (date1.compareTo(date2) == 0) {

				 cc.setBalance(cc.getBalance()-200);
				 cc.setDernier_frais(todayDate);
				 saveOrUpdateCompte_cheque(cc);
				 System.out.println(c+ " dernier frais works");
			 }
		 }


	 }
	public Date addYear(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, i);
        return cal.getTime();
    }
	

}
