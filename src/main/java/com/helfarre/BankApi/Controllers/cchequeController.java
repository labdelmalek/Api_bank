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

import com.helfarre.BankApi.Entities.Transactionepaepa;
import com.helfarre.BankApi.Entities.ccheque;
import com.helfarre.BankApi.Entities.compte_cheque;
import com.helfarre.BankApi.Exceptions.ProduitIntrouvableException;
import com.helfarre.BankApi.Services.ClientService;
import com.helfarre.BankApi.Services.TransactionImpl;
import com.helfarre.BankApi.Services.cchequeService;
import com.helfarre.BankApi.Services.chequeService;
import com.helfarre.BankApi.Services.compteService;
@RestController
	@RequestMapping("/ccheque/*")
public class cchequeController {
	@Autowired
	private chequeService servi;
	@Autowired
	private compteService cpservice;

		@Autowired
		private cchequeService cchequeServi;	
		@Autowired
		private ClientService clserv;
		@Autowired
		private TransactionImpl transactionService;
		
		@GetMapping(value = "/carnet")
		public ResponseEntity<List<ccheque>> getAllUsers() {
			List<ccheque> users = cchequeServi.getAllCcheque();
			return new ResponseEntity<List<ccheque>>(users, HttpStatus.OK);
		}
		@GetMapping(value = "/{idcarnet}")
		public ResponseEntity<?> GetcchequeById(@PathVariable Long idcarnet) {
			Optional<ccheque> users = cchequeServi.getCchequeById(idcarnet);
			if (users.isPresent()) {
			return new ResponseEntity<Optional<ccheque>>(users, HttpStatus.OK);
			}
			else {
				throw new ProduitIntrouvableException("carnet introuvable");

			}

		}
		@GetMapping(value = "/carnets/{idcompte}")
		public ResponseEntity<?> GetcarnetsById(@PathVariable Long idcompte) {
			Optional<compte_cheque> test=servi.getCompte_chequeById(idcompte);
			if(test.isPresent()) {
			Optional<List<ccheque>> users = cchequeServi.getCchequeByCompte(test.get());
			if (users.isPresent()) {
			return new ResponseEntity<Optional<List<ccheque>>>(users, HttpStatus.OK);
			}
			else {
				throw new ProduitIntrouvableException("carnet introuvable");

			}}else {
				throw new ProduitIntrouvableException("compte introuvable");

			}

		}
		@PostMapping(value = "/addCarnet/{idcompte}")
		@Transactional
		public ResponseEntity<?> SaveUser(@RequestBody ccheque comptes , @PathVariable Long idcompte) {
			Optional<compte_cheque> cl =servi.getCompte_chequeById(idcompte);
			if(cl.isPresent()) {


				if(servi.verifconditioncarte(cl.get())==true) {
					comptes.setRib(cl.get().getRib());
					comptes.setCompte(cl.get());
					comptes.setFrais_ch(50);
					comptes.setNb_pages(50);
					ccheque  accountupdated = cchequeServi.saveOrUpdateCcheque(comptes);
					
					

					Transactionepaepa ttt=new Transactionepaepa();
					Date date = new Date();
					ttt.setDate(date);
					ttt.setSomme(comptes.getFrais_ch());
			        ttt.setTransactionStatus(2);
			        ttt.setReceiver(cpservice.getCompteByNuminternational("agence"+comptes.getCompte().getLagence().getId()).get());
			        ttt.setSender(comptes.getCompte());
			        ttt.setType("fraiscarnet");
			        ttt.setPerson(null);
			        transactionService.saveTransaction(ttt);
					


					   cpservice.getCompteByNuminternational("agence"+cl.get().getLagence().getId()).get().setBalance(cpservice.getCompteByNuminternational("agence"+cl.get().getLagence().getId()).get().getBalance()+50);
					   cpservice.saveOrUpdateCompte( cpservice.getCompteByNuminternational("agence"+cl.get().getLagence().getId()).get());
					  
					
					clserv.sendemail(comptes.getCompte().getClient().getEmail(), "On vous informe que votre carnet est désormais disponible pour le compte dont le N° international est: "+comptes.getCompte().getNuminternational());

					return new ResponseEntity<ccheque>(accountupdated, HttpStatus.OK);
				}
				throw new ProduitIntrouvableException("Compte suspendu ou solde insuffisant");

			}
			else {
				throw new ProduitIntrouvableException("compte introuvable");

			}

		}

	
	}
