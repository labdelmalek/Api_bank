package com.helfarre.BankApi.Services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.helfarre.BankApi.Entities.Agence;
import com.helfarre.BankApi.Entities.cbancaire;
import com.helfarre.BankApi.Entities.compte;
import com.helfarre.BankApi.Entities.credit;
import com.helfarre.BankApi.Entities.historiqueadmin;
import com.helfarre.BankApi.Repositories.creditRepo;
import com.helfarre.BankApi.Repositories.historiqueRepos;
@Service
public class histoimpl  implements historiServ{
	@Autowired
	private AgenceService agserv;
	@Autowired
	private compteService cpserv;
	@Autowired 
	private creditRepo crrepo;
	@Autowired
	private historiqueRepos historepo;
	

	
	@Scheduled(cron = "0 22 22 * * ?")

    public void executer() {
		List<Agence>ag=agserv.findAll();
		Date date=new Date();
		for(int i=0;i<ag.size();i++) {
			Optional<List<compte>> comptes=cpserv.getCompteByAgenceId(ag.get(i).getId());
			double balance=0;
			double creditt=0;
			if(comptes.isPresent()) {
				for(int j=0;j<comptes.get().size();j++) {
					if(!comptes.get().get(j).getTypecompte().toString().equalsIgnoreCase("banque")){
						System.out.println(balance);
						balance=balance+comptes.get().get(j).getBalance();
						
					}
				}
			}
			Optional<List<credit>> credt=crrepo.findByCreditLagenceId(ag.get(i).getId());
			if(credt.isPresent()) {
				for(int f=0;f<credt.get().size();f++)
				{
					if(credt.get().get(f).isStatut()==true&&credt.get().get(f).getMoisrest()>0) {
						creditt= creditt+credt.get().get(f).getMensualite()*credt.get().get(f).getMoisrest();
						
					}
				}
			}
			
			
			
			
			 historiqueadmin test=new historiqueadmin();
			 
			 
			 test.setIdagence(ag.get(i).getId());
			 test.setJour(date);
			 test.setRestecredit(creditt);
			 test.setSoldeagence(cpserv.getCompteByNuminternational("agence"+ag.get(i).getId()).get().getBalance());
			 test.setSoldeclients(balance);
			 historepo.save(test);
			
			 
		}
	


      
          



      }
	
	
	
	
	

	@Override
	public List<historiqueadmin> getallbyagence(Long idagence) {
		
		return historepo.findByIdagence(idagence);
	}

}
