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
import com.helfarre.BankApi.Entities.cheque;
import com.helfarre.BankApi.Exceptions.ProduitIntrouvableException;
import com.helfarre.BankApi.Services.ClientService;
import com.helfarre.BankApi.Services.TransactionImpl;
import com.helfarre.BankApi.Services.chequeService;
import com.helfarre.BankApi.Services.chequesService;
@RestController
@RequestMapping("/cheques")
public class chequesController {


	@Autowired
	private chequeService compteService;	
	
	@Autowired
	private com.helfarre.BankApi.Services.compteService comservice;	

	@Autowired
	private chequesService comptesService;	
	@Autowired
	private ClientService clserv;
	@Autowired 
	private TransactionImpl transactionService;
	@Autowired
	private com.helfarre.BankApi.Services.compteService cpserv;
	
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<cheque>> getAllUsers() {
		List<cheque> users = comptesService.findAll();
		return new ResponseEntity<List<cheque>>(users, HttpStatus.OK);
	}
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> GetchequeById(@PathVariable Long id) {
		Optional<cheque> users = comptesService.getChequeById(id);
		if (users.isPresent()) {
		return new ResponseEntity<cheque>(users.get(), HttpStatus.OK);
		}
		else {
			throw new ProduitIntrouvableException("cheque avec id  "+id+" inexistant ");
		}

	}
	@GetMapping(value = "/compte/sendercheque/{numcompte}")
	public ResponseEntity<?> Getsendchequebynumcompte(@PathVariable String numcompte) {
		Optional<List<cheque>> users = comptesService.getChequeBycomptesender(numcompte);
		
		if (users.isPresent()) {
		return new ResponseEntity<List<cheque>>(users.get(), HttpStatus.OK);
		}
		else {
			throw new ProduitIntrouvableException("numcompte inexistant ou compte avec aucun cheque ");
		}

	}
	@GetMapping(value = "/compte/receivercheque/{numcompte}")
	public ResponseEntity<?> Getreceivchequebynumcompte(@PathVariable String numcompte) {
		Optional<List<cheque>> users = comptesService.getChequeBycomptereceiver(numcompte);
		
		if (users.isPresent()) {
		return new ResponseEntity<List<cheque>>(users.get(), HttpStatus.OK);
		}
		else {
			throw new ProduitIntrouvableException("numcompte inexistant ou compte avec aucun cheque ");
		}

	}
	@GetMapping(value = "/compte/getallcheque/{numcompte}")
	public ResponseEntity<?> Getallchequebynumcompte(@PathVariable String numcompte) {
		Optional<List<cheque>> users = comptesService.getallchequecompte(numcompte);
		
		if (users.isPresent()) {
		return new ResponseEntity<List<cheque>>(users.get(), HttpStatus.OK);
		}
		else {
			throw new ProduitIntrouvableException("numcompte inexistant ou compte avec aucun cheque. ");
		}

	}


	@PostMapping(value = "/encaisserespece")
	@Transactional
	public ResponseEntity<?> Savecheque(@RequestBody cheque cp ) {
		if(compteService.getCompte_chequeByNuminternational(cp.getNuminternational()).isPresent()) {
		Date date2 =new Date();
		cp.setDate_encaissement(date2);
		cp.setTypeencaissement("espece");
		cp.setStatus(0);
		cp.setCompte(compteService.getCompte_chequeByNuminternational(cp.getNuminternational()).get());
		cp.setNumcheque(compteService.getCompte_chequeByNuminternational(cp.getNuminternational()).get().getRib());
		
		if(comptesService.encaisserchequeespeces(cp)==true) {
			cp.setStatus(2);
			comptesService.saveOrUpdateCheque(cp);
			clserv.sendemail(cp.getCompte().getClient().getEmail(), "On vous informe qu'un cheque par la somme "+cp.getMontant()+"a ete encaisser éspece pour Mr"+cp.getBenificiaire());
			Transactionepaepa ttt=new Transactionepaepa();
			Date date = new Date();
			ttt.setDate(date);
			ttt.setSomme(cp.getMontant());
	        ttt.setTransactionStatus(2);
	        ttt.setReceiver(null);
	        ttt.setSender(cp.getCompte());
	        ttt.setType("encaissementespece");
	        ttt.setPerson(cp.getBenificiaire());
	        transactionService.saveTransaction(ttt);
	        Transactionepaepa ttt2=new Transactionepaepa();
	        			
	        			Date date3 = new Date();
	        			ttt2.setDate(date3);
	        	        ttt2.setTransactionStatus(2);
	        	        ttt2.setReceiver(null);
	        	        ttt2.setSender(cpserv.getCompteByNuminternational("agence"+cp.getCompte().getLagence().getId()).get());
	        	        ttt2.setType("encaissementespece");
	        	        ttt2.setPerson(null);
	        	        ttt2.setBq(ttt.getBq());
	        	        transactionService.saveTransaction(ttt);
	        			   cpserv.getCompteByNuminternational("agence"+cp.getCompte().getLagence().getId()).get().setBalance(cpserv.getCompteByNuminternational("agence"+cp.getCompte().getLagence().getId()).get().getBalance()-cp.getMontant());
	        			   cpserv.saveOrUpdateCompte( cpserv.getCompteByNuminternational("agence"+cp.getCompte().getLagence().getId()).get());	
		}	
		else {
			comptesService.saveOrUpdateCheque(cp);
			//solde insuffisant
			return new ResponseEntity<Long>(new Long(-1),HttpStatus.OK);
		}}
		else {
			//numinternational inexistant
			return new ResponseEntity<Long>(new Long(-2),HttpStatus.OK);
		}
		return new ResponseEntity<Long>(new Long(1),HttpStatus.OK);
	}
	@PostMapping(value = "/encaissercompte")
	@Transactional
	public ResponseEntity<?> encaissementcheque(@RequestBody cheque cp ) {
		System.out.println(cp.getBenificiaire()+"   "+cp.getNuminternational()+" "+cp.getMontant());
		if(compteService.getCompte_chequeByNuminternational(cp.getNuminternational()).isPresent()
				&& comservice.getCompteByNuminternational(cp.getBenificiaire()).isPresent()) {
			System.out.println("iiiiiiiiiiiiii");
		Date date2 =new Date();
		cp.setDate_encaissement(date2);
		cp.setTypeencaissement("compte");
		cp.setStatus(0);
		cp.setCompte(compteService.getCompte_chequeByNuminternational(cp.getNuminternational()).get());
		cp.setNumcheque(cp.getCompte().getRib());
		
			comptesService.saveOrUpdateCheque(cp);
			clserv.sendemail(cp.getCompte().getClient().getEmail(), "On vous informe qu'un cheque par la somme "+cp.getMontant()+"va s'encaisser dans les prochaines 48 heures");

		}
		else {
//			throw new ProduitIntrouvableException("numinternational ou benificiaire inexistant");
			return new ResponseEntity<Long>(new Long(-1),HttpStatus.OK); 
		}
		
		return new ResponseEntity<cheque>(cp,HttpStatus.OK);
			
	}
	
}
