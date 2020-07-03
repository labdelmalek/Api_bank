package com.helfarre.BankApi.Controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.helfarre.BankApi.Entities.Agence;
import com.helfarre.BankApi.Entities.Client;
import com.helfarre.BankApi.Entities.Transactionepaepa;
import com.helfarre.BankApi.Entities.compte_cheque;
import com.helfarre.BankApi.Exceptions.ProduitIntrouvableException;
import com.helfarre.BankApi.Repositories.AgenceJpaRepository;
import com.helfarre.BankApi.Repositories.chequeRepository;
import com.helfarre.BankApi.Services.ClientService;
import com.helfarre.BankApi.Services.TransactionImpl;
import com.helfarre.BankApi.Services.chequeService;
import com.helfarre.BankApi.Services.compteService;
@RestController
@RequestMapping("/cheque")
public class chequeController {
	@Autowired 
	private AgenceJpaRepository agencerepo;
	@Autowired
	private  compteService cpservice;

	@Autowired
	private chequeService compteService;	
	@Autowired
	private ClientService servi;	
	@Autowired
	private chequeRepository chrep;
	@Autowired
	private TransactionImpl transactionservice;
	
	@GetMapping(value = "/comptes")
	public ResponseEntity<List<compte_cheque>> getAllUsers() {
		List<compte_cheque> users = compteService.findAll();
		return new ResponseEntity<List<compte_cheque>>(users, HttpStatus.OK);
	}
	@GetMapping(value = "/comptes/{id}")
	public ResponseEntity<?> GetCompte_chequeById(@PathVariable Long id) {
		Optional<compte_cheque> users = compteService.getCompte_chequeById(id);
		if (users.isPresent()) {
		return new ResponseEntity<compte_cheque>(users.get(), HttpStatus.OK);
		}
		else {
			throw new ProduitIntrouvableException("id compte introuvable ");
		}

	}
	@GetMapping(value = "/comptes/client/{idclient}")
	public ResponseEntity<?> GetCompteByClient(@PathVariable Long idclient) {
		List<compte_cheque> users = chrep.findByClient_Id(idclient);
		
		if (!users.isEmpty()) {
		return new ResponseEntity<List<compte_cheque>>(users, HttpStatus.OK);
		}
		else {
			throw new ProduitIntrouvableException("Le client avec l'id " + idclient + " est INTROUVABLE. ");
		}

	}

	
	@PostMapping(value = "/addCompte/{idclient}/{idagence}")
	@Transactional
	public ResponseEntity<?>  SaveUser(@RequestBody compte_cheque comptes,@PathVariable Long idclient ,@PathVariable Long idagence) {
		
		Optional<Client> cl =servi.getClientById(idclient);
		if(cl.isPresent()) {
				Client client= cl.get();
				String var=chrep.getnumer();
				while(var.length()<16)	{
					var=0+var;
				
				}
				Optional<Agence> ag=agencerepo.findById(idagence);
				if(ag.isPresent()) {
				String agence=agencerepo.findById(idagence).get().getNumagence();
				comptes.setNuminternational(agence+var);
				String test=""+compteService.getreste(comptes.getNuminternational());

				if(test.length()<2) {
					test=0+test;
				}
				comptes.setRib(comptes.getNuminternational()+test);
				comptes.setNumcompte(var);
				comptes.setFrais(200);
				chrep.test();
				comptes.setTypecompte("cheque");
				Date date2 =new Date();
				comptes.setLagence(agencerepo.findById(idagence).get());

                comptes.setDernier_frais(date2);
                comptes.setCreation_date(date2);
                comptes.setIs_suspended(false);
			comptes.setClient(client);
			
			compteService.saveOrUpdateCompte_cheque(comptes);
			
			Transactionepaepa ttt=new Transactionepaepa();
			Date date = new Date();
			ttt.setDate(date);
			ttt.setSomme(comptes.getFrais());
	        ttt.setTransactionStatus(2);
	        ttt.setReceiver(cpservice.getCompteByNuminternational("agence"+comptes.getLagence().getId()).get());
	        ttt.setSender(comptes);
	        ttt.setType("fraiscompte");
	        ttt.setPerson(null);
	        transactionservice.saveTransaction(ttt);
			


			   cpservice.getCompteByNuminternational("agence"+comptes.getLagence().getId()).get().setBalance(cpservice.getCompteByNuminternational("agence"+comptes.getLagence().getId()).get().getBalance()+200+comptes.getBalance());
			   cpservice.saveOrUpdateCompte( cpservice.getCompteByNuminternational("agence"+comptes.getLagence().getId()).get());
			  
			
			servi.sendemail(comptes.getClient().getEmail(), "On confirme l'ouverture de votre compte \n"+"N° international: "+comptes.getNuminternational()+"\nSolde:"+comptes.getBalance()+"\n Type : "+comptes.getTypecompte());

			}else {
				
				// agence introuvable 
				return new ResponseEntity<Integer>(-1,HttpStatus.OK);
			}
		}
		else {
			// Client non disponible

			return new ResponseEntity<Integer>(-2,HttpStatus.OK);
		}
		return new ResponseEntity<compte_cheque>(comptes,HttpStatus.OK);
			
	}	
	@GetMapping(value = "numinternational/{comptes}")
	public ResponseEntity<?> GetCompte_epargneBynuminternational(@PathVariable String comptes ) {
		Optional<compte_cheque> users = compteService.getCompte_chequeByNuminternational(comptes);
		if (users.isPresent()) {
		return new ResponseEntity<compte_cheque>(users.get(),HttpStatus.OK);
		}
		else {
			throw new ProduitIntrouvableException("compte introuvable");

		}

	}
	@GetMapping(value = "numcompte/{comptes}")
	public ResponseEntity<?> GetCompte_epargneBynumcompte(@PathVariable String comptes) {
		Optional<compte_cheque> users = compteService.getCompte_chequeByNum_compte(comptes);
		if (users.isPresent()) {
		return new ResponseEntity<compte_cheque>(users.get(),HttpStatus.OK);
		}
		else {
			throw new ProduitIntrouvableException("compte introuvable");

		}

	}
	/*	
	@PutMapping(value = "/retraitCompte/{id}/{somme}")
	public ResponseEntity<?> retrait(@PathVariable Long somme, @PathVariable Long id) {
		
		if(compteService.retraitcompte(id,somme)==true) {
		
		return new ResponseEntity<String>("ajoute", HttpStatus.OK);
		}else {
			throw new ProduitIntrouvableException("solde insuffisant ou compte inexistant");

		}
		
	}
	@PutMapping(value = "/versementCompte/{id}/{somme}")
	public ResponseEntity<?> versement(@PathVariable Long somme, @PathVariable Long id) {
		compte_cheque cmp=compteService.getCompte_chequeById(id);
		
		if(compteService.creditcompte(id,somme)==true)
		
		return new ResponseEntity<String>("ajoute", HttpStatus.OK);
		else {
			throw new ProduitIntrouvableException("Compte suspendu ou indisponible");

			}
		}
	*/	

}
