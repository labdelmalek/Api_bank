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

import com.helfarre.BankApi.Entities.compte_epargne;
import com.helfarre.BankApi.Repositories.epargneRepository;
@Service
public class epargneServiceImpl implements epargneService{
	@Autowired
	private epargneRepository epargneRepo;

	
	
	@Scheduled(cron = "0 0 4 1 * ?")
	
	  public void executer() {
		}
	
	
	
		@Override
		public Optional<List<compte_epargne>> findByClient(Long id) {
			Optional<List<compte_epargne>> epargne=epargneRepo.findByClient_Id(id);
			return epargne;
		}

		@Override
		public List<compte_epargne> findAll() {
			return epargneRepo.findAll();		

		}

	

		@Override
		public Optional<compte_epargne> getCompte_epargneById(long id) {
			return epargneRepo.findById(id);

		}

		@Override
		public compte_epargne saveOrUpdateCompte_epargne(compte_epargne epargne) {
			epargneRepo.save(epargne);		
			return epargne;
		}

		@Override
		public Optional<compte_epargne> getCompte_epargneByNum_compte(String num_compte) {
			Optional<compte_epargne> epargne=epargneRepo.findByNumcompte(num_compte);
			return epargne;
		}

		@Override
		public Optional<compte_epargne> getCompte_epargneByNuminternational(String numinternational) {
			Optional<compte_epargne> epargne=epargneRepo.findByNuminternational(numinternational);
			return epargne;		}
//date dernier modif
	/*	@Override
		public void datedernier(long id) {
			Optional<compte_epargne> cheque=epargneRepo.findById(id);
			if (cheque.isPresent()) {
			Date date2 =new Date();
			
			cheque.get().setDernier_interet(date2);
			saveOrUpdateCompte_epargne(cheque.get());		
			}	
		}*/
/*
		@Override
		public boolean retraitcompte(long id, float somme) {
			Optional<compte_epargne> cheque=epargneRepo.findById(id);
			if (cheque.isPresent()) {

			if(cheque.get().getIs_suspended()==false && cheque.get().getBalance()>somme) {
			cheque.get().setBalance(cheque.get().getBalance()-somme);
			saveOrUpdateCompte_epargne(cheque.get());
			datedernier(id);
			return true;
			}
			}
			return false;
		}
		
		@Override
		public boolean creditcompte(long id, float somme) {
			Optional<compte_epargne> cheque=epargneRepo.findById(id);
			if (cheque.isPresent()) {
			if(cheque.get().getIs_suspended()==false) {
			cheque.get().setBalance(cheque.get().getBalance()+somme-1);
			saveOrUpdateCompte_epargne(cheque.get());
			return true;
			}}
			return false;
		}
	*/	
	/*	@Override
		public boolean verifconditionadd(compte_epargne compte){
			String numc=compte.getNumcompte();
			String numint=compte.getNuminternational();
			if(getCompte_epargneByNum_compte(numc).isPresent()==false && getCompte_epargneByNuminternational(numint).isPresent()==false) {
				return true;
			}
			return false;
		}
*/
		@Override
		public Optional<List<compte_epargne>> findByClient_agence_Id(Long id) {
			//banquiers a agence
			return epargneRepo.findByLagence_Id(id);
		}
		
		
		@Scheduled(cron = "0 0 4 * * ?")
		 public void executer1() throws ParseException {

			 Date todayDate=new Date();


			 compte_epargne cc=null;
			 //for(compte_cheque c: findAll()) { 

			 for(int c=0;c<findAll().size();c++) {

				 cc=epargneRepo.findAll().get(c);

				 Date da=addYear(cc.getDernier_interet(),1);
				 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
				 String date1= formatter.format(todayDate);
				 String date2=formatter.format(da);


				 System.out.println(c+" "+date2+"  "+date1);
				 if (date1.compareTo(date2) == 0) {
					 Double balance=cc.getBalance()+(cc.getBalance() * 0.02);
					 cc.setBalance(balance);

					 cc.setDernier_interet(todayDate);
					 saveOrUpdateCompte_epargne(cc);
					 System.out.println(c+ " dernier interet works");
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
