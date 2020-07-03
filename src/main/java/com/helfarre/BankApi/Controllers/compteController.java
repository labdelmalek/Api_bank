package com.helfarre.BankApi.Controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helfarre.BankApi.Entities.Transactionepaepa;
import com.helfarre.BankApi.Entities.compte;
import com.helfarre.BankApi.Entities.credit;
import com.helfarre.BankApi.Exceptions.ProduitIntrouvableException;
import com.helfarre.BankApi.Services.ClientService;
import com.helfarre.BankApi.Services.TransactionImpl;
import com.helfarre.BankApi.Services.compteService;
import com.helfarre.BankApi.Services.creditService;

@RestController
@RequestMapping("/compte")

public class compteController  {
	@Autowired 
	private ClientService clserv;
	@Autowired
	private compteService compteServi;
	@Autowired
	private TransactionImpl transactionService;
	@Autowired
	private creditService crdserv;
	@GetMapping(value = "/comptes")
	public ResponseEntity<List<compte>> getAllUsers() {
		List<compte> users = compteServi.findAll();
		return new ResponseEntity<List<compte>>(users, HttpStatus.OK);
	}
	@GetMapping(value = "/{idcompte}")
	public ResponseEntity<?> GetCompteById(@PathVariable Long idcompte) {
		Optional<compte> users = compteServi.getCompteById(idcompte);
		if (users.isPresent()) {
		return new ResponseEntity<compte>(users.get(),HttpStatus.OK);
		}
		else {
			throw new ProduitIntrouvableException("compte introuvable");

		}
	}
	@GetMapping(value = "comptes/{idclient}")
	public ResponseEntity<?> GetComptesByIdclient(@PathVariable Long idclient) {
		Optional<List<compte>> users = compteServi.getCompteByClient(idclient);
		if (users.isPresent()) {
		return new ResponseEntity<Optional<List<compte>>>(users,HttpStatus.OK);
		}
		else {
			throw new ProduitIntrouvableException("compte introuvable");

		}

	}
	@PutMapping(value = "/retrait/{id}")
	public ResponseEntity<?> retrait(@RequestBody Transactionepaepa test, @PathVariable Long id) {
		Optional<compte> cl2 =compteServi.getCompteById(id);
		if(compteServi.retraitcompte(id,test.getSomme())==true) {
	
		compte cl11=cl2.get();
		Date date = new Date();
		test.setDate(date);
        test.setTransactionStatus(2);
        test.setReceiver(null);
        test.setSender(cl11);
        test.setType("retrait");
        test.setPerson(null);
        transactionService.saveTransaction(test);
        
        
        compteServi.getCompteByNuminternational("agence"+cl2.get().getLagence().getId()).get().setBalance(compteServi.getCompteByNuminternational("agence"+cl2.get().getLagence().getId()).get().getBalance()-test.getSomme());
		   compteServi.saveOrUpdateCompte( compteServi.getCompteByNuminternational("agence"+cl2.get().getLagence().getId()).get());

		Transactionepaepa ttt=new Transactionepaepa();
		Date date3 = new Date();
		ttt.setDate(date3);
		ttt.setSomme(test.getSomme());
        ttt.setTransactionStatus(2);
        ttt.setReceiver(null);
        ttt.setSender(compteServi.getCompteByNuminternational("agence"+cl2.get().getLagence().getId()).get());
        ttt.setType("retrait");
        ttt.setPerson(null);
        ttt.setBq(test.getBq());
        transactionService.saveTransaction(ttt);
		
//        Everything was done well
		return new ResponseEntity<Long>(new Long(1), HttpStatus.OK);

		}else {
//		throw new ProduitIntrouvableException("compte introuvable ou solde insuffisant");
			return new ResponseEntity<Long>(new Long(-1), HttpStatus.OK);

		}
		
	}
	
	@GetMapping(value = "numinternational/{comptes}")
	public ResponseEntity<?> GetCompte_epargneBynuminternational(@PathVariable String comptes ) {
		Optional<compte> users = compteServi.getCompteByNuminternational(comptes);
		if (users.isPresent()) {
		return new ResponseEntity<compte>(users.get(),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Long>(new Long(-1),HttpStatus.OK);

		}

	}
	@GetMapping(value = "agence/{idagence}")
	public ResponseEntity<?> Getcomptebyagence(@PathVariable Long idagence ) {
		Optional<List<compte>> users = compteServi.getCompteByAgenceId(idagence);
		if (users.isPresent()) {
		return new ResponseEntity<List<compte>>(users.get(),HttpStatus.OK);
		}
		else {
			throw new ProduitIntrouvableException("id agence introuvable");

		}

	}
	@GetMapping(value = "numcompte/{comptes}")
	public ResponseEntity<?> GetCompte_epargneBynumcompte(@PathVariable String comptes) {
		Optional<compte> users = compteServi.getCompteByNum(comptes);
		if (users.isPresent()) {
		return new ResponseEntity<compte>(users.get(),HttpStatus.OK);
		}
		else {
			throw new ProduitIntrouvableException("numcompte introuvable");

		}

	}
	
	
	@PutMapping(value = "/versement/{numinternational}")
    public ResponseEntity<?> versement(@RequestBody Transactionepaepa test, @PathVariable String numinternational) {
        Optional<compte> cmp=compteServi.getCompteByNuminternational(numinternational);

        if(cmp.isPresent() &&cmp.get().getIs_suspended()==false) {
        compteServi.creditcompte(cmp.get().getId(),test.getSomme());
        
        
        compteServi.getCompteByNuminternational("agence"+cmp.get().getLagence().getId()).get().setBalance(compteServi.getCompteByNuminternational("agence"+cmp.get().getLagence().getId()).get().getBalance()+test.getSomme());
		   compteServi.saveOrUpdateCompte( compteServi.getCompteByNuminternational("agence"+cmp.get().getLagence().getId()).get());
		   
		
		
		Date date = new Date();
		test.setDate(date);
        test.setTransactionStatus(2);
        test.setReceiver(cmp.get());
        test.setSender(null);
        test.setType("versement");
        transactionService.saveTransaction(test);
		Transactionepaepa ttt=new Transactionepaepa();
		Date date3 = new Date();
		ttt.setDate(date3);
		ttt.setSomme(test.getSomme());
        ttt.setTransactionStatus(2);
        ttt.setReceiver(compteServi.getCompteByNuminternational("agence"+cmp.get().getLagence().getId()).get());
        ttt.setSender(null);
        ttt.setType("versement");
        ttt.setPerson(null);
        ttt.setBq(test.getBq());
        transactionService.saveTransaction(ttt);
        return new ResponseEntity<Integer>(1, HttpStatus.OK);
        }else {
            throw new ProduitIntrouvableException("compte introuvable");
        }}
	
	@PutMapping(value = "/activercompte/{id}")
	public ResponseEntity<?> activercompte(@PathVariable Long id) {
		Optional<compte> cmp=compteServi.getCompteById(id);
		if(cmp.isPresent()&& cmp.get().getIs_suspended()==true) {
		cmp.get().setIs_suspended(false);
		compteServi.saveOrUpdateCompte(cmp.get());
		clserv.sendemail(cmp.get().getClient().getEmail(), "On confirme l'activation de votre compte dont le "+"N° international est: "+cmp.get().getNuminternational());

		return new ResponseEntity<String>("activé", HttpStatus.OK);}
		else {
			throw new ProduitIntrouvableException("compte introuvable ou deja activé");
		}
	}
	@PutMapping(value = "/fermercompte/{id}")
	public ResponseEntity<?> fermercompte( @PathVariable Long id) {
		System.out.println("asdskjdflajdhflkjahdflkajhflkajdhfklajdshflkajshdfkljasdhfklajdshflkasdjhfklajhdflakjdhflkajhdflk");
		System.out.println();
		Optional<compte> cmp=compteServi.getCompteById(id);
		if (cmp.isPresent() && cmp.get().getIs_suspended() == false) 
		{
			if(cmp.get().getBalance()<1) 
			{
				Optional<List<credit>> crdt=crdserv.getCreditByCompte(cmp.get());
			if(crdt.isPresent()) {
				for(int i=0;i<crdt.get().size();i++) {
					if(crdt.get().get(i).isStatut()==true&&crdt.get().get(i).getMoisrest()>0)
//						throw new ProduitIntrouvableException("compte avec un credit");
						return new ResponseEntity<Integer>(-1,HttpStatus.OK);
				}
				
		}
			double montant=cmp.get().getBalance();
/*
Transactionepaepa ttt2=new Transactionepaepa();
			
			Date date2 = new Date();
			ttt2.setDate(date2);
	        ttt2.setTransactionStatus(2);
	        ttt2.setReceiver(null);
	        ttt2.setSomme((float) montant);
	        ttt2.setSender(compteServi.getCompteByNuminternational("agence"+cmp.get().getLagence().getId()).get());
	        ttt2.setType("fermeturecompte");
	        ttt2.setPerson(null);
	        transactionService.saveTransaction(ttt2);
	        */
		cmp.get().setBalance(0);
		cmp.get().setIs_suspended(true);
		 compteServi.saveOrUpdateCompte(cmp.get());
			clserv.sendemail(cmp.get().getClient().getEmail(), "On confirme la suspension de votre compte dont le "+"N° international est: "+cmp.get().getNuminternational());
			
		return new ResponseEntity<Double>(montant, HttpStatus.OK);
	}
			else {
//				throw new ProduitIntrouvableException("compte avec balance superieur à 1");
				return new ResponseEntity<Integer>(-2,HttpStatus.OK);

			}}
		else {
//			throw new ProduitIntrouvableException("compte introuvable ou deja desactivé");
			return new ResponseEntity<Integer>(-3,HttpStatus.OK);

		}

}
}
