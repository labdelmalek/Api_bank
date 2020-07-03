package com.helfarre.BankApi.Controllers;

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

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.helfarre.BankApi.Entities.Client;
import com.helfarre.BankApi.Entities.RequestCC;
import com.helfarre.BankApi.Entities.RequestCartCredit;
import com.helfarre.BankApi.Entities.cbancaire;
import com.helfarre.BankApi.Entities.compte_cheque;
import com.helfarre.BankApi.Exceptions.RequestException;
import com.helfarre.BankApi.Repositories.ClientJpaRepository;
import com.helfarre.BankApi.Repositories.cbancaireRepository;
import com.helfarre.BankApi.Services.ClientService;
import com.helfarre.BankApi.Services.RequestCartCreditService;
import com.helfarre.BankApi.Services.cbancaireService;
import com.helfarre.BankApi.Services.chequeService;

@RestController
@RequestMapping("/requestCartCredit/*")
public class RequestCartCreditController {
	
	@Autowired
	private RequestCartCreditService requestCartCreditService;
	
	@Autowired
	private chequeService compteChequeService;
	
	@Autowired
	private cbancaireService carte;
	@Autowired
	private ClientService clserv;
	@Autowired
	private ClientJpaRepository clientRepo;
	@Autowired
	private cbancaireRepository carterepo;

	@PostMapping("/saveWithAccount/{numinternational}")
    @Transactional
    public ResponseEntity<?> saveWithCompte(@RequestBody RequestCartCredit requestCartCredit, @PathVariable String numinternational) {
        Optional<compte_cheque> compteCheque = compteChequeService.getCompte_chequeByNuminternational(numinternational);

        if (compteCheque.isPresent())
        {    
            System.out.println("ehhhhhhhhhhhh"+requestCartCredit.getCardType());
            Optional<List<cbancaire>> cartebancaire = carte.getCbancaireByCompte(compteCheque.get());
            System.out.println("asdasdasdasd");
            List<RequestCartCredit> req = requestCartCreditService.getRequestCartCreditByCompteCheque(compteCheque.get());
            
            for (int i = 0; i<req.size() ;  i++) {
                if(req.get(i).getCloseRequest() == false) {
                	return new ResponseEntity<Long>(new Long(-1), HttpStatus.OK);
                	}
            }
            if(cartebancaire.isPresent()) {
            for (int j = 0; j<cartebancaire.get().size() ; j++) {
                if(cartebancaire.get().get(j).isStatus() == true) {
                	return new ResponseEntity<Long>(new Long(-1), HttpStatus.OK);                }
                else {
                    carterepo.delete(cartebancaire.get().get(j));
                }
            }
            }
            requestCartCredit.setCheque(compteCheque.get());;
            requestCartCredit.setCloseRequest(false);
            Date date2 =new Date();
            requestCartCredit.setDateRequest(date2);
            RequestCartCredit requst = requestCartCreditService.saveRequestCartCredit(requestCartCredit);
            clserv.sendemail(requst.getCheque().getClient().getEmail(), "On vous confirme  que votre demande de carte bancaire à été prise en compte pour le compte "+requst.getCheque().getNumcompte());
            return new ResponseEntity<RequestCartCredit>(requst, HttpStatus.OK);
        }
        else 
        	return new ResponseEntity<Long>(new Long(-1), HttpStatus.OK);
    }

	@DeleteMapping(value = "/{idRequest}/deleteRequest")
	public ResponseEntity<?> deleteRequest(@PathVariable Long idRequest) {
		
		Optional<RequestCartCredit> requestCartCredit= requestCartCreditService.getRequestCartCreditById(idRequest);
		if(requestCartCredit.isPresent()) {
			requestCartCreditService.deleteRequestCartCredit(idRequest);

		return new ResponseEntity<RequestCartCredit>(requestCartCredit.get(),HttpStatus.OK);
		}
		else {
			throw new RequestException("requete introuvable");
		}
	}
	
	@GetMapping(value = "/{idRequest}")
	public ResponseEntity<?> getRequestCartCreditById(@PathVariable Long idRequest) {
		Optional<RequestCartCredit> requestCartCredit = requestCartCreditService.getRequestCartCreditById(idRequest);
		if(requestCartCredit.isPresent()){
			return new ResponseEntity<RequestCartCredit>(requestCartCredit.get(), HttpStatus.OK);
		}
		else {
			throw new RequestException("Cette requete est introuvanle veuillez essayer avec un autre ID, merci pour votre attention");
		}
	}
	
	//For updating status of a request
	@PutMapping("/terminer/{idRequest}")
	public ResponseEntity<?> terminerStatus( @PathVariable Long idRequest) {
		
		Optional<RequestCartCredit> requst= requestCartCreditService.getRequestCartCreditById(idRequest);
		if(requst.isPresent()) {
		requst.get().setCloseRequest(true);
		RequestCartCredit updatedRequest = requestCartCreditService.updateRequestCartCredit(requst.get());
		return new ResponseEntity<RequestCartCredit>(updatedRequest, HttpStatus.OK);
	}else {
		throw new RequestException("Cette requete est introuvable ");
	}}
	
	
	//for getting an activate request by account
	@GetMapping("/EnCoursdeTraitementbyCompte/{idCompte}")
	public List<RequestCartCredit> findAllByCompte(@PathVariable Long idCompte) {
		Optional<compte_cheque> compteCheque = compteChequeService.getCompte_chequeById(idCompte);


		if(compteCheque.isPresent()){
			List<RequestCartCredit> req = requestCartCreditService.getRequestCartCreditByCompteCheque(compteCheque.get());
			List<RequestCartCredit> list = new LinkedList<>();
			 
			for (int i = 0; i<req.size() ;  i++) {
				if(req.get(i).getCloseRequest() == false) {	
				list.add(req.get(i));
				}
			}	
		return list;	
		}
		else
		{
			throw new RequestException("Cette requete est introuvanle veuillez essayer avec un autre ID, merci pour votre attention");		}
	}
	
	
	
	//display finished requests by account
	@GetMapping("finisedbyCompte/{idCompte}")
	public List<RequestCartCredit> finishedByCompte(@PathVariable Long idCompte) {
		Optional<compte_cheque> compteCheque = compteChequeService.getCompte_chequeById(idCompte);
;

		if(compteCheque.isPresent()){
			List<RequestCartCredit> req = requestCartCreditService.getRequestCartCreditByCompteCheque(compteCheque.get());
			List<RequestCartCredit> list = new LinkedList<>();
			 
			for (int i = 0; i<req.size() ;  i++) {
				if(req.get(i).getCloseRequest() == true) {	
				list.add(req.get(i));
				}
			}	
		return list;	
		}
		else
		{
			throw new RequestException("Cette requete est introuvanle veuillez essayer avec un autre ID, merci pour votre attention");		}
	}
	
		@GetMapping(value = "/ByCompte/{idCompte}")
		public ResponseEntity<?> findRequestCarnetCByCompte(@PathVariable Long idCompte) {
			Optional<compte_cheque> cl= compteChequeService.getCompte_chequeById(idCompte);
			if(cl.isPresent()) {
			List<RequestCartCredit> requestcartcredit = requestCartCreditService.getRequestCartCreditByCompteCheque(cl.get());
				return new ResponseEntity<List<RequestCartCredit>>(requestcartcredit, HttpStatus.OK);
			}
			else {
				throw new RequestException("compte inexistant");
			}
		}		
		@GetMapping(value = "/ByClient/{idClient}")
        public ResponseEntity<?> findRequestCarnetCByClient(@PathVariable Long idClient) {
            Optional<Client> cl= clientRepo.findById(idClient);
            if(cl.isPresent()) {
            List<RequestCartCredit> requestrv = requestCartCreditService.getRequestByClient(cl.get());

                return new ResponseEntity<List<RequestCartCredit>>(requestrv, HttpStatus.OK);
            }
            else {
                throw new RequestException("Client No found");
            }
        }
}
