 package com.helfarre.BankApi.Controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

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

import com.helfarre.BankApi.Entities.compte_epargne;
import com.helfarre.BankApi.Exceptions.ProduitIntrouvableException;
import com.helfarre.BankApi.Repositories.AgenceJpaRepository;
import com.helfarre.BankApi.Repositories.epargneRepository;
import com.helfarre.BankApi.Services.ClientService;
import com.helfarre.BankApi.Services.chequeService;
import com.helfarre.BankApi.Services.compteService;
import com.helfarre.BankApi.Services.epargneService;
@RestController
@RequestMapping("/epargne/*")
public class epargneController {
	
@Autowired
private ClientService clserv;

		@Autowired
		private epargneService epargneServi;	
		@Autowired
		private epargneRepository epargnerepo;
		@Autowired 
		private AgenceJpaRepository agencerepo;
		@Autowired 
		private  chequeService chserv;
		@Autowired
		private compteService cpserv;
		
		@GetMapping(value = "/comptes")
		public ResponseEntity<List<compte_epargne>> getAllUsers() {
			List<compte_epargne> users = epargneServi.findAll();
			return new ResponseEntity<List<compte_epargne>>(users, HttpStatus.OK);
		}
		@GetMapping(value = "/{id}")
		public ResponseEntity<?> GetCompte_epargneById(@PathVariable Long id) {
			Optional<compte_epargne> users = epargneServi.getCompte_epargneById(id);
			if (users.isPresent()) {
			return new ResponseEntity<compte_epargne>(users.get(),HttpStatus.OK);
			}
			else {
				throw new ProduitIntrouvableException("compte introuvable");

			}

		}
		@GetMapping(value = "numinternational/{comptes}")
		public ResponseEntity<?> GetCompte_epargneBynuminternational(@PathVariable String comptes ) {
			Optional<compte_epargne> users = epargneServi.getCompte_epargneByNuminternational(comptes);
			if (users.isPresent()) {
			return new ResponseEntity<compte_epargne>(users.get(),HttpStatus.OK);
			}
			else {
				throw new ProduitIntrouvableException("compte introuvable(numinternational)");

			}

		}
		@GetMapping(value = "numcompte/{comptes}")
		public ResponseEntity<?> GetCompte_epargneBynumcompte(@PathVariable String comptes) {
			Optional<compte_epargne> users = epargneServi.getCompte_epargneByNum_compte(comptes);
			if (users.isPresent()) {
			return new ResponseEntity<compte_epargne>(users.get(),HttpStatus.OK);
			}
			else {
				throw new ProduitIntrouvableException("compte introuvable(numcompte)");

			}

		}
	
		@PostMapping(value = "/addCompte/{id}/{idagence}")
		@Transactional
		public ResponseEntity<?> SaveUser(@Valid @RequestBody compte_epargne comptes,@PathVariable long id ,@PathVariable long idagence ) {
			Optional<Client> cl =clserv.getClientById(id);
			if(cl.isPresent()) {
				Client test =cl.get();
				String var=epargnerepo.getnumer();
				while(var.length()<16)	{
					var=0+var;
				}
				Optional<Agence> ag=agencerepo.findById(idagence);
				if(ag.isPresent()) {
				comptes.setNumcompte(var);
				comptes.setLagence(agencerepo.findById(idagence).get());
				epargnerepo.test();
				comptes.setTaux(2);
				comptes.setClient(test);
				String agence=comptes.getLagence().getNumagence();
				comptes.setNuminternational(agence+var);
				Date date2 =new Date();
				comptes.setTypecompte("epargne");
                comptes.setDernier_interet(date2);
                comptes.setCreation_date(date2);
                comptes.setIs_suspended(false);
                String test2=""+chserv.getreste(comptes.getNuminternational());
				if(test2.length()<2) {
					test2=0+test2;
				}
				comptes.setRib(comptes.getNuminternational()+test2);
				compte_epargne  accountupdated = epargneServi.saveOrUpdateCompte_epargne(comptes);


				   cpserv.getCompteByNuminternational("agence"+comptes.getLagence().getId()).get().setBalance(cpserv.getCompteByNuminternational("agence"+comptes.getLagence().getId()).get().getBalance()+comptes.getBalance());
				   cpserv.saveOrUpdateCompte( cpserv.getCompteByNuminternational("agence"+comptes.getLagence().getId()).get());
				  
				clserv.sendemail(accountupdated.getClient().getEmail(), "On confirme l'ouverture de votre compte \n"+"N° international: "+accountupdated.getNuminternational()+"\nSolde:"+accountupdated.getBalance()+"\n Type : "+accountupdated.getTypecompte());
				return new ResponseEntity<compte_epargne>(accountupdated, HttpStatus.OK);
				}
			
			else {
				throw new ProduitIntrouvableException("agence introuvable");

			}
			}
			else {
				throw new ProduitIntrouvableException("client introuvable");

			}
		
		}

	/*	
		@PutMapping(value = "/retrait/{id}/{somme}")
		public ResponseEntity<?> retrait(@PathVariable Long somme, @PathVariable Long id) {
			System.out.println("test");
			if(epargneServi.retraitcompte(id,somme)==true) {
			
			return new ResponseEntity<String>("ajoute", HttpStatus.OK);
			}else {
			throw new ProduitIntrouvableException("client introuvable");

			}
			
		}
		@PutMapping(value = "/versement/{id}/{somme}")
		public ResponseEntity<?> versement(@PathVariable Long somme, @PathVariable Long id) {
			Optional<compte_epargne> cmp=epargneServi.getCompte_epargneById(id);
			System.out.println("test");
			if(cmp.isPresent()) {
			epargneServi.creditcompte(id,somme);
			
			return new ResponseEntity<String>("ajoute", HttpStatus.OK);
			}else {
				throw new ProduitIntrouvableException("compte introuvable");
			}}
		*/	
	

	}
