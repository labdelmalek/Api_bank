package com.helfarre.BankApi.Controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helfarre.BankApi.Entities.Agence;
import com.helfarre.BankApi.Entities.Transactionepaepa;
import com.helfarre.BankApi.Entities.compte;

import com.helfarre.BankApi.Entities.credit;
import com.helfarre.BankApi.Repositories.creditRepo;
import com.helfarre.BankApi.Repositories.infocreditRepo;
import com.helfarre.BankApi.Services.AgenceService;
import com.helfarre.BankApi.Services.ClientService;
import com.helfarre.BankApi.Services.TransactionImpl;
import com.helfarre.BankApi.Services.compteService;
import com.helfarre.BankApi.Services.creditService;


@RestController
@RequestMapping("/credit")
public class creditController {

	@Autowired
	private creditRepo crrepo;

	@Autowired
	private infocreditRepo inforepo;

	@Autowired
	private AgenceService agenceserv;
	@Autowired
	private creditService creditserv;
	@Autowired
	private compteService compteserv;
	@Autowired
	private ClientService clserv;
	@Autowired
	private TransactionImpl transactionservice;

	
	@GetMapping("/credits")
	public List<credit> findAll() {
		return creditserv.findAll();
	}
	@GetMapping(value = "/client/{idcl}")
    public ResponseEntity<?> getAllCreditsclient(@PathVariable Long idcl) {
        List<credit>cll=new ArrayList<credit>();
        Optional<List<compte>> comp= compteserv.getCompteByClient(idcl);
        
        if(comp.isPresent()) {
        for ( compte c : comp.get()) {
            Optional<List<credit>>clll=creditserv.getCreditByCompte(c);
            if(clll.isPresent()) {
            for(credit s : clll.get()) {
                if(s.isStatut()==true && s.getMoisrest()>0) {
                	System.out.println(s.toString());
                    cll.add(s);
                }
            }
            }
        }
        return new ResponseEntity<List<credit>>(cll,HttpStatus.OK);
            }
        else
//            return new ResponseEntity<Error>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<Long>(new Long(-1),HttpStatus.OK);

    }
	
	@GetMapping("/{id}")
	public Optional<credit> findCreditById(@PathVariable Long id) {
		return creditserv.getCreditById(id);
	}
	@GetMapping(value = "/{id}/credits")
	public ResponseEntity<?> getAllCreditscompte(@PathVariable Long id) {
		Optional<compte> cl= compteserv.getCompteById(id);
		if(cl.isPresent()) {
		Optional<List<credit>> Accounts = creditserv.getCreditByCompte(cl.get());
		return new ResponseEntity<Optional<List<credit>>>(Accounts,HttpStatus.OK);
			}
		else
			return new ResponseEntity<Error>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping(value = "/{id}/agence")
	public ResponseEntity<?> getAllCreditsagence(@PathVariable Long id) {
		
		Optional<List<credit>> credits = crrepo.findByCreditLagenceId(id);
		List<credit> cred=new ArrayList<credit>();
		if(credits.isPresent()) {
		for(int i=0;i<credits.get().size();i++) {
			if(credits.get().get(i).isStatut()==false)cred.add(credits.get().get(i));
		}}
		return new ResponseEntity<List<credit>>(cred,HttpStatus.OK);
		
	}
	@DeleteMapping(value = "/{id}/delete")
	public ResponseEntity<?> refusercredit(@PathVariable Long id) {
		
		Optional<credit> credits = crrepo.findById(id);
		
		if(credits.isPresent()) {
			clserv.sendemail(credits.get().getCredit().getClient().getEmail(), "On vous confirme que votre  dossier de crédit  dont le numero est "+credits.get().getId()+" a été rejeté " );

		crrepo.delete(credits.get());
		inforepo.delete(inforepo.findByIdcredit(id));

		}
		return new ResponseEntity<>(1,HttpStatus.OK);
		
	}
	
	
	
	
	

	
	@PostMapping(value = "/demandecredit/{id}")
	@Transactional
	public ResponseEntity<?> demandecredit(@RequestBody credit cr,@PathVariable Long id) {
		System.out.println(cr.getTaux()+" "+cr.getNbmois()+ " "+cr.getSomme());
		Optional<compte> test=compteserv.getCompteById(id);
		if(test.isPresent()) {
		cr.setCredit(test.get());
		cr.setStatut(false);
		cr.setMoisrest(cr.getNbmois());
		float var=creditserv.simulationcredit(cr.getNbmois(), cr.getSomme(), cr.getTaux());
		cr.setMensualite(var);
		creditserv.saveOrUpdateCredit(cr);
		clserv.sendemail(cr.getCredit().getClient().getEmail(), "On vous informe que votre demande de credit est prise en compte pour le compte dont le N° international est: "+cr.getCredit().getNuminternational());

		return new ResponseEntity<credit>(cr,HttpStatus.OK);
		}
		else {
//			return new ResponseEntity<String>("id compte inexistant",HttpStatus.NO_CONTENT);
			return new ResponseEntity<Long>(new Long(-1),HttpStatus.OK);

		}
	}
	 @PutMapping("/activerCredit/{id}")
	   public ResponseEntity<?> activercredit( @PathVariable Long id){
		   Optional<credit> cln=creditserv.getCreditById(id);
		   if(cln.isPresent()) {
		   (cln.get()).setStatut(true);
		   
		   compteserv.getCompteByNuminternational("agence"+cln.get().getCredit().getLagence().getId()).get().setBalance(compteserv.getCompteByNuminternational("agence"+cln.get().getCredit().getLagence().getId()).get().getBalance()-cln.get().getSomme());
		   compteserv.saveOrUpdateCompte( compteserv.getCompteByNuminternational("agence"+cln.get().getCredit().getLagence().getId()).get());
		   
		   (cln.get()).getCredit().setBalance( (cln.get()).getCredit().getBalance()+ (cln.get()).getSomme());
		   compteserv.saveOrUpdateCompte( (cln.get()).getCredit());
		   credit cr=creditserv.saveOrUpdateCredit(cln.get());
			clserv.sendemail(cr.getCredit().getClient().getEmail(), "On vous informe que votre credit a été validé pour le compte dont le N° international est: "+cr.getCredit().getNuminternational()+" solde actuel  :"+cr.getCredit().getBalance());

			
			  Transactionepaepa ttt=new Transactionepaepa();
				Date date = new Date();
				ttt.setDate(date);
				ttt.setSomme((cln.get()).getSomme());
		        ttt.setTransactionStatus(2);
		        ttt.setReceiver(cln.get().getCredit());
		        ttt.setSender(compteserv.getCompteByNuminternational("agence"+cln.get().getCredit().getLagence().getId()).get());
		        ttt.setType("credit");
		        ttt.setPerson(null);
		        transactionservice.saveTransaction(ttt);
			  
			
		   
		   return new ResponseEntity<credit>(cr,HttpStatus.OK);
		   
	   }
		   else{
				return new ResponseEntity<String>("id compte inexistant",HttpStatus.NO_CONTENT);

			   }
		   }
	 
	 @GetMapping("/simulerrestecredit/{id}")
     public ResponseEntity<?> simulationrestecredit( @PathVariable Long id){
           Optional<credit> cln=creditserv.getCreditById(id);
           if(cln.isPresent()) {

           float reste=cln.get().getMoisrest()*cln.get().getMensualite();
           return new ResponseEntity<Float>(reste,HttpStatus.OK);
           }
           else {
               return new ResponseEntity<Integer>(-1,HttpStatus.OK); 
           }
     }
	 @PutMapping("/payerrestecredit/{id}")
	   public ResponseEntity<?> payerrestecredit( @PathVariable Long id){
		   Optional<credit> cln=creditserv.getCreditById(id);
		   if(cln.isPresent()) {
		   (cln.get()).setStatut(true);
		   float reste=cln.get().getMoisrest()*cln.get().getMensualite();
		   
		   	if((cln.get()).getCredit().getBalance()>reste) {
		   	 compteserv.getCompteByNuminternational("agence"+cln.get().getCredit().getLagence().getId()).get().setBalance(compteserv.getCompteByNuminternational("agence"+cln.get().getCredit().getLagence().getId()).get().getBalance()+reste);
			   compteserv.saveOrUpdateCompte( compteserv.getCompteByNuminternational("agence"+cln.get().getCredit().getLagence().getId()).get());
			   (cln.get()).getCredit().setBalance( (cln.get()).getCredit().getBalance()- reste);
			   cln.get().setMoisrest(0);
			   compteserv.saveOrUpdateCompte( (cln.get()).getCredit());
			   credit cr=creditserv.saveOrUpdateCredit(cln.get());
				clserv.sendemail(cr.getCredit().getClient().getEmail(), "On vous confirme le paiement du reste de votre credit dont la somme "+reste+" du  compte dont le  N° international est: "+cr.getCredit().getNuminternational()+" solde actuel  :"+cr.getCredit().getBalance());

				  Transactionepaepa ttt=new Transactionepaepa();
					Date date = new Date();
					ttt.setDate(date);
					ttt.setSomme((cln.get()).getSomme());
			        ttt.setTransactionStatus(2);
			        ttt.setSender(cln.get().getCredit());
			        ttt.setReceiver(compteserv.getCompteByNuminternational("agence"+cln.get().getCredit().getLagence().getId()).get());
			        ttt.setType("restecredit");
			        ttt.setPerson(null);
			        transactionservice.saveTransaction(ttt);
			   		return new ResponseEntity<credit>(cr,HttpStatus.OK);

		   	}
		  
		 
		   	else {
		   		//solde insuffisant
		   		return new ResponseEntity<Integer>(-2,HttpStatus.OK);
		   	}}
		   else {
			   //id credit non existant
			   return new ResponseEntity<Integer>(-1,HttpStatus.OK);
		   }

			
	 }


}
