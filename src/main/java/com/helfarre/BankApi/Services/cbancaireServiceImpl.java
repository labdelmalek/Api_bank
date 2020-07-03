package com.helfarre.BankApi.Services;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.helfarre.BankApi.Entities.cbancaire;
import com.helfarre.BankApi.Entities.compte_cheque;

import com.helfarre.BankApi.Repositories.cbancaireRepository;

@Service
@EnableScheduling

public class cbancaireServiceImpl implements cbancaireService {
	@Autowired
	private cbancaireRepository cbancaireRepo;
	
	@Override
	public List<cbancaire> getAllCbancaire() {
		return cbancaireRepo.findAll();
	}

	@Override
	public Optional<cbancaire> getCbancaireById(Long id) {
		Optional<cbancaire> epargne=cbancaireRepo.findById(id);
		

		return epargne;
	}


	@Override
	public cbancaire saveOrUpdateCbancaire(cbancaire carte) {
		cbancaireRepo.save(carte);		
		return carte;
	}

	@Override
	public Optional<cbancaire> deleteCbancaire(Long id) {
		Optional<cbancaire> epargne=cbancaireRepo.findById(id);
		cbancaireRepo.deleteById(id);
		return epargne;	
		
	}

	@Override
	public Optional<cbancaire> getCbancaireByNumcarte(String num) {
Optional<cbancaire> epargne=cbancaireRepo.findByNumcarte(num);
		

		return epargne;
	}

	@Override
	public Optional<List<cbancaire>> getCbancaireByCompte(compte_cheque compte) {
		Optional<List<cbancaire>> carte=cbancaireRepo.findByCompte(compte);
		return carte;
	}
	
	


	/*
	 * 
	@Override
	public boolean verificonditioncarte(compte_cheque compte,cbancaire carte) {
		float test=carteRepo.findByNom(carte.getNom_carte()).getPrix_carte();
		if(compte.getIs_suspended()==false&&compte.getBalance()>test) {
			compte.setBalance(compte.getBalance()-test);
			saveOrUpdateCompte_cheque(compte);
			return true;
			
		}
		return false;
	}*/
	 
	/*
	@Modifying
	@Query("update cbancaire u set u.retraitjour=0")
*/

	
	
	
	
/*	@Modifying
	@Query(" UPDATE `cbancaire` t SET t.retraitjour=0 where 1 ")
	
	*/
	
	/*
	@Scheduled(cron = "* * * * * ?")
	public void retraitfrais() {
		cbancaire test =null;
		Date type=null;
		for (int i = 0; i < getAllCbancaire().size(); i++) {
			System.out.println("hdbhdb");
			 Date now = new Date();
			
			test=cbancaireRepo.findAll().get(i);
			type=  cbancaireRepo.findAll().get(i).getExpiration();
			//Duration duration =Duration.between(now, type);
			// int days = Days.daysBetween(now, type).getDays();

			 System.out.println(now.getDate());
			 System.out.println(type.getD);
			 
			 
			 long diffInMillies = Math.abs(now.getDate() - type.getDate());
			 System.out.println(diffInMillies);
			
		}
	}
	*/
	
	
	
	@Scheduled(cron = "0 0 0 * * ?")

    public void executer() {



      cbancaire test =null;
      for (int i = 0; i < getAllCbancaire().size(); i++) {


        test=  cbancaireRepo.findAll().get(i);
        test.setRetraitjour(0);
          saveOrUpdateCbancaire(test);



      }
	
		
	//    System.out.println("Début exécution de la tâche MaTache " + new Date());
	    
	//    System.out.println("Fin exécution de la tâche MaTache " + new Date());
	  }
	@Scheduled(cron = "* 19 22 * * ?")
	public void executer1() {

		Date todayDate=new Date();

		cbancaire test =null;

		for(cbancaire c :getAllCbancaire()) {

			if(todayDate.after(c.getExpiration())) {
				Long id=c.getId();
				this.deleteCbancaire(id);
				System.out.println("it works CB");
			}
		}

	}
}
