package com.helfarre.BankApi.Controllers;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helfarre.BankApi.Entities.Transactionepaepa;
import com.helfarre.BankApi.Entities.cbancaire;
import com.helfarre.BankApi.Entities.compte_cheque;
import com.helfarre.BankApi.Exceptions.ProduitIntrouvableException;
import com.helfarre.BankApi.Repositories.cbancaireRepository;
import com.helfarre.BankApi.Repositories.chequeRepository;
import com.helfarre.BankApi.Services.ClientService;
import com.helfarre.BankApi.Services.TransactionImpl;
import com.helfarre.BankApi.Services.cbancaireService;
import com.helfarre.BankApi.Services.chequeService;
import com.helfarre.BankApi.Services.compteService;

@RestController
@RequestMapping("/cbancaire")
public class cbancaireController {

	@Autowired
	private cbancaireService cbancaireServi;	

	@Autowired
	private chequeService chequeServi;
	
	@Autowired
	private cbancaireRepository cbancaireRepositoryImpl;
	@Autowired
	private TransactionImpl transactionservice;
	@Autowired
	private compteService cpservice;

	@Autowired
	private chequeRepository chequeRepositoryImpl;
	@Autowired
	private ClientService clserv;
	
	
	@GetMapping(value = "/cartes")
	public ResponseEntity<List<cbancaire>> getAllUsers() {
		List<cbancaire> users = cbancaireServi.getAllCbancaire();
		return new ResponseEntity<List<cbancaire>>(users, HttpStatus.OK);
	}
	@GetMapping(value = "/carte/{idcarte}")
	public ResponseEntity<?> GetCbancaireById(@PathVariable Long idcarte) {
		Optional<cbancaire> users = cbancaireServi.getCbancaireById(idcarte);
		if (users.isPresent()) {
		return new ResponseEntity<Optional<cbancaire>>(users, HttpStatus.OK);
		}
		else {
			throw new ProduitIntrouvableException("carte  introuvable");
		}
	}
	@GetMapping(value = "/cartes/{idcompte}")
    public ResponseEntity<?> GetCbancaireByIdcompte(@PathVariable Long idcompte) {
        Optional<compte_cheque> users = chequeServi.getCompte_chequeById(idcompte);
        if(users.isPresent()) {
        Optional<List<cbancaire>>test = cbancaireServi.getCbancaireByCompte(users.get());
        if(test.isPresent()) {
        if(test.get() != null) {
        return new ResponseEntity<List<cbancaire>>(test.get(), HttpStatus.OK);
        }
        else {
//            throw new ProduitIntrouvableException("aucune carte pour se compte");
            return new ResponseEntity<Long>(new Long(-1), HttpStatus.OK);
            }
        }
        else
            return new ResponseEntity<Long>(new Long(-1), HttpStatus.OK);

            }
        else{
//            throw new ProduitIntrouvableException("compte introuvable ");
            return new ResponseEntity<Long>(new Long(-2), HttpStatus.OK);
        }
    }
	@PostMapping(value ="/addCarte/{idcompte}")
	public ResponseEntity<?> SaveUser(@RequestBody cbancaire comptes,@PathVariable Long idcompte) {
		
		
			Optional<compte_cheque> cl =chequeRepositoryImpl.findById(idcompte);
			if(cl.isPresent()){
			compte_cheque compteCheque =cl.get();
			String var = cbancaireRepositoryImpl.getnumer();
			
			while(var.length()<8)	{
				var=0+var;
				System.out.println(var);
				
			}
			
			 int x = (int)(Math.random()*((999)+1));
			 System.out.println(x);
			 String test=""+x;
			 while(test.length()<3) {test=0+test;
			 }
			 comptes.setCvv(test);
			 int y = (int)(Math.random()*((9999)+1));
			 System.out.println(y);
			 String test2=""+y;
			 while(test2.length()<4) {test2=0+test2;
			 }
			 comptes.setMdp(Integer.parseInt(test2));
			 Calendar calendar = Calendar.getInstance();
		       calendar.add(Calendar.YEAR, +5);
		       comptes.setExpiration(calendar.getTime());
			String agence=compteCheque.getLagence().getNumagence();
			comptes.setNumcarte(agence+var);
			comptes.setStatus(true);
			cbancaireRepositoryImpl.test();
			if(chequeServi.verificonditioncarte(compteCheque,comptes) == true) {

/*
				   cpservice.getCompteByNuminternational("agence"+comptes.getCompte().getLagence().getId()).get().setBalance(cpservice.getCompteByNuminternational("agence"+comptes.getCompte().getLagence().getId()).get().getBalance()+comptes.getBalance());
				   cpservice.saveOrUpdateCompte( cpservice.getCompteByNuminternational("agence"+comptes.getCompte().getLagence().getId()).get());
				  */
			comptes.setCompte(compteCheque);
			//System.out.println(comptes.getNumcarte());
			cbancaireRepositoryImpl.save(comptes);
			clserv.sendemail(comptes.getCompte().getClient().getEmail(), "On vous informe que votre carte est désormais disponible pour le compte dont le N° international est: "+comptes.getCompte().getNuminternational()+"   .Mot de passe de votre est"+comptes.getMdp()+".    Attention:votre mot de passe ne doit etre communiquer à personne ");

			return new ResponseEntity<cbancaire>(comptes, HttpStatus.OK);
			}
			else {
				
				throw new ProduitIntrouvableException(" compte suspendu ou solde insuffisant");
			}}
		else {
			throw new ProduitIntrouvableException("compte introuvable");
		}
	}

	@PutMapping(value = "/activerCarte/{id}")
    public ResponseEntity<?> activercarte( @PathVariable Long id) {
        Optional<cbancaire> test=cbancaireServi.getCbancaireById(id);
        if(test.isPresent() && test.get().isStatus() == false) {
            test.get().setStatus(true);
            cbancaire accountupdated = cbancaireServi.saveOrUpdateCbancaire(test.get());
            clserv.sendemail(accountupdated.getCompte().getClient().getEmail(), "On vous informe que votre carte, dont le N°  est: "+accountupdated.getNumcarte()+" ,est active");

        return new ResponseEntity<cbancaire>(accountupdated, HttpStatus.OK);
        }
        else {
//            throw new ProduitIntrouvableException("deja activer ou carte non existante");
            return new ResponseEntity<Long>(new Long(-1), HttpStatus.OK);
            }
    }
    @PutMapping(value = "/bloquerCarte/{id}")
    public ResponseEntity<?> bloquercarte( @PathVariable Long id) {
        Optional<cbancaire> test=cbancaireServi.getCbancaireById(id);
        if(test.isPresent()&&test.get().isStatus()==true) {
            test.get().setStatus(false);
            cbancaire accountupdated = cbancaireServi.saveOrUpdateCbancaire(test.get());
            clserv.sendemail(accountupdated.getCompte().getClient().getEmail(), "On vous informe que votre carte, dont le N°  est: "+accountupdated.getNumcarte()+" ,est bloqué");

        return new ResponseEntity<cbancaire>(accountupdated, HttpStatus.OK);
        }
        else {
//            throw new ProduitIntrouvableException("deja activer ou carte non existante");
            return new ResponseEntity<Long>(new Long(-1), HttpStatus.OK);
            }
    }
	@PutMapping(value = "/retraitcarte/{idcarte}/{somme}")
	public ResponseEntity<?> retraitcarte( @PathVariable Long idcarte,  @PathVariable int somme) {
		Optional<cbancaire> test=cbancaireServi.getCbancaireById(idcarte);
		if(test.isPresent()) {
		if(chequeServi.retraitcarte(test.get().getCompte().getId(),somme,test.get())==true) {
			Transactionepaepa ttt=new Transactionepaepa();
			
			Date date = new Date();
			ttt.setDate(date);
	        ttt.setTransactionStatus(2);
	        ttt.setReceiver(null);
	        ttt.setSender(test.get().getCompte());
	        ttt.setSomme(somme);
	        ttt.setType("retraitcarte");
	        ttt.setPerson(null);
	        transactionservice.saveTransaction(ttt);
			
Transactionepaepa ttt2=new Transactionepaepa();
			
			Date date2 = new Date();
			ttt2.setDate(date2);
	        ttt2.setTransactionStatus(2);
	        ttt2.setReceiver(null);
	        ttt2.setSomme(somme);
	        ttt2.setSender(cpservice.getCompteByNuminternational("agence"+test.get().getCompte().getLagence().getId()).get());
	        ttt2.setType("retraitcarte");
	        ttt2.setPerson(null);
	        transactionservice.saveTransaction(ttt);
	        

			   cpservice.getCompteByNuminternational("agence"+test.get().getCompte().getLagence().getId()).get().setBalance(cpservice.getCompteByNuminternational("agence"+test.get().getCompte().getLagence().getId()).get().getBalance()-somme);
			   cpservice.saveOrUpdateCompte( cpservice.getCompteByNuminternational("agence"+test.get().getCompte().getLagence().getId()).get());
			   
			
			
			clserv.sendemail(test.get().getCompte().getClient().getEmail(), "On vous informe que vous avez retiré par votre carte bancaire dont le N°  est: "+test.get().getNumcarte()+" la somme de :"+somme+"\n Solde actuel: "+test.get().getCompte().getBalance());

			return new ResponseEntity<String>("retrait effectue", HttpStatus.OK);
		}else {
			throw new ProduitIntrouvableException("solde insuffisant ou plafond atteint");

			}
		}else {
			throw new ProduitIntrouvableException("carte introuvable");
			}
	}
	
	
	
}
