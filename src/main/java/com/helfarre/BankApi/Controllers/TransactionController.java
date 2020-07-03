package com.helfarre.BankApi.Controllers;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helfarre.BankApi.Entities.Client;
import com.helfarre.BankApi.Entities.Transactionepaepa;
import com.helfarre.BankApi.Entities.compte;
import com.helfarre.BankApi.Exceptions.ProduitIntrouvableException;
import com.helfarre.BankApi.Exceptions.ResourceNotFoundException;
import com.helfarre.BankApi.Repositories.ClientJpaRepository;
import com.helfarre.BankApi.Services.TransactionService;
import com.helfarre.BankApi.Services.compteService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private compteService accountservice;
	
	@Autowired
	private ClientJpaRepository clientRepo;
	
	@GetMapping(value = "/transactions")
	public ResponseEntity<List<Transactionepaepa>> getAlltransactions() {
		List<Transactionepaepa> trans = transactionService.getAllTransactions();
		return new ResponseEntity<List<Transactionepaepa>>(trans, HttpStatus.OK);
	}
	
	@GetMapping(value = "transactions/{id}")
	public ResponseEntity<?> GetTransactionById(@PathVariable Long id) {
		Optional<Transactionepaepa> trans = transactionService.getTransactionById(id);
		if (trans.isPresent()) {
		return new ResponseEntity<Transactionepaepa>(trans.get(), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("id transaction non dispponible",HttpStatus.NO_CONTENT);
		}
	}
	
	/* ### get all transactions of a sender Account ### */
	
	@GetMapping(value = "senderAccount/{id}")
    public ResponseEntity<?> GetTransactionBySenderAccount(@PathVariable Long id)throws ResourceNotFoundException {
        Optional<compte> cp = accountservice.getCompteById(id);
        if(cp.isPresent()) {
        List<Transactionepaepa> transa = transactionService.getSenderTransactions(id);

        return new ResponseEntity<List<Transactionepaepa>>(transa, HttpStatus.OK);
        }
        else {
            throw new ResourceNotFoundException(" ce compte n'existe pas");
        }
    }
	
	
	/* ### get all transactions of a receiver Account ### */
	
	@GetMapping(value = "receiverAccount/{id}")
    public ResponseEntity<?> GetTransactionByReceiverAccount(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<compte> cp = accountservice.getCompteById(id);
        if(cp.isPresent()) {
        List<Transactionepaepa> transa = transactionService.getReceiverTransactions(id);

        return new ResponseEntity<List<Transactionepaepa>>(transa, HttpStatus.OK);
        }
        else {
            throw new ResourceNotFoundException(" ce compte n'existe pas");
        }
    }
	
	/* ### get all transactions of an Account ### */
	
	@GetMapping(value = "account/{accountId}")
    public ResponseEntity<?> GetAllTransactionsByAccountsansvirement(@PathVariable Long accountId) throws ResourceNotFoundException {
        Optional<compte> cp = accountservice.getCompteById(accountId);
        if(cp.isPresent()) {
        List<Transactionepaepa> receiverTransaction = transactionService.getReceiverTransactions(accountId);
        receiverTransaction.addAll(transactionService.getSenderTransactions(accountId));

        return new ResponseEntity<List<Transactionepaepa>>(receiverTransaction, HttpStatus.OK);}
        else {
            throw new ResourceNotFoundException("compte   introuvable");
        }
        }
		
	
	@GetMapping(value = "transall/{accountId}")
    public ResponseEntity<?> GetAllTransactionsavecvirementbyaccount(@PathVariable Long accountId) throws ResourceNotFoundException {
        Optional<compte> cp = accountservice.getCompteById(accountId);
        if(cp.isPresent()) {
        List<Transactionepaepa> Transaction = transactionService.getalltransactionsbyId(accountId);
 

        return new ResponseEntity<List<Transactionepaepa>>(Transaction, HttpStatus.OK);}
        else {
            throw new ResourceNotFoundException("compte   introuvable");
        }
        }

	@PostMapping(value = "/sendTransaction/{idreceiver}/{idsender}")
	public ResponseEntity<?> getReceiverTransactionsById_receiver(@RequestBody Transactionepaepa trans,@PathVariable Long idreceiver,@PathVariable Long idsender) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			Optional<Client> client = clientRepo.findByEmail(((UserDetails)principal).getUsername());
			if(client.isPresent()) {
				Optional<List<compte>> comptes=accountservice.getCompteByClient(client.get().getId());
				Boolean indicateur=false;
				if(comptes.isPresent()) {
					for(compte cmpt : comptes.get()) {
						if(cmpt.getId()==idsender) {
							indicateur=true;
							break;
						}
					}
				}
				if(indicateur == false)
					return new ResponseEntity<String>("error",HttpStatus.UNAUTHORIZED);
			}
			}
		Optional<compte> cl =accountservice.getCompteById(idreceiver);
		Optional<compte> cl2 =accountservice.getCompteById(idsender);
		if(cl.isPresent() && cl2.isPresent()) {
		compte cl11=cl2.get();
		compte cl1=cl.get();
		Date date = new Date();
		trans.setDate(date);
        trans.setTransactionStatus(0);
        trans.setReceiver(cl1);
        trans.setSender(cl11);
        System.out.println("somme" + trans.getSomme());
        trans.setPerson(null);
        trans.setType("transaction");
        transactionService.saveTransaction(trans);
		 if(accountservice.retraitcompte(idsender, trans.getSomme())==true) {trans.setTransactionStatus(1);
		 transactionService.saveTransaction(trans);
		 accountservice.getCompteByNuminternational("agence"+cl11.getLagence().getId()).get().setBalance(accountservice.getCompteByNuminternational("agence"+cl11.getLagence().getId()).get().getBalance()-trans.getSomme());
		   accountservice.saveOrUpdateCompte( accountservice.getCompteByNuminternational("agence"+cl11.getLagence().getId()).get());
		   accountservice.getCompteByNuminternational("agence"+cl1.getLagence().getId()).get().setBalance(accountservice.getCompteByNuminternational("agence"+cl1.getLagence().getId()).get().getBalance()+trans.getSomme());
		   accountservice.saveOrUpdateCompte( accountservice.getCompteByNuminternational("agence"+cl1.getLagence().getId()).get());
		  
		 
		 
		 }
		 else throw new ProduitIntrouvableException("solde insuffisant");
		 if(accountservice.creditcompte(idreceiver, trans.getSomme())==true) {
			 trans.setTransactionStatus(2);
			 transactionService.saveTransaction(trans);
			return new ResponseEntity<Integer>(1, HttpStatus.OK);}
		}
		else {
			  throw new ProduitIntrouvableException("idcompte indisponible");
		}
		return null;

}
	@GetMapping(value = "idaccount/{accountnum}")
	public ResponseEntity<?> GetAccountByNAccount(@PathVariable String accountnum) {
		Optional<compte> cl = accountservice.getCompteByNuminternational(accountnum);
		if (cl.isPresent()) {
			long id=cl.get().getId();
		return new ResponseEntity<Long>(id, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Long>(new Long(-1),HttpStatus.OK);
		}
	}	
	
}
