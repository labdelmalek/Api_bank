package com.helfarre.BankApi.Controllers;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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

import com.helfarre.BankApi.Entities.Client;
import com.helfarre.BankApi.Entities.RequestCC;
import com.helfarre.BankApi.Entities.compte_cheque;
import com.helfarre.BankApi.Exceptions.RequestException;
import com.helfarre.BankApi.Repositories.ClientJpaRepository;
import com.helfarre.BankApi.Repositories.chequeRepository;
import com.helfarre.BankApi.Services.ClientService;
import com.helfarre.BankApi.Services.RequestCCService;
import com.helfarre.BankApi.Services.chequeService;
@RestController
@RequestMapping("/requestCC")
public class RequestCCController {

	@Autowired
	private RequestCCService requestCCService;

	@Autowired
	private chequeRepository compteChequeRepository;
	@Autowired
	private chequeService compteChequeService;
	@Autowired
	private ClientJpaRepository clientRepo;
	@Autowired
	private ClientService clserv;
	
	
	
	
	
	//Ajout d'une requête par compte cheque
	
	@PutMapping("/saveWithAccount/{numinternational}")
    public ResponseEntity<?> saveWithCompte( @PathVariable String numinternational) {
        Optional<compte_cheque> compteCheque = compteChequeService.getCompte_chequeByNuminternational(numinternational);
        RequestCC requestCC = new RequestCC();
        if (compteCheque.isPresent())
        {
            List<RequestCC> req = requestCCService.getRequestCCByCompteCheque(compteCheque.get());
            for (int i = 0; i<req.size() ;  i++) {
                if(req.get(i).getCloseRequest() == false) {
//                    throw new RequestException("vous ne pouvez pas faire une requête, vous avez déjà une en cours");
                    return new ResponseEntity<Long>(new Long(-1), HttpStatus.OK);
                }
            }
            requestCC.setCheque(compteCheque.get());;
            requestCC.setCloseRequest(false);
            Date date2 =new Date();
            requestCC.setDateRequest(date2);
            RequestCC requst = requestCCService.saveRequestCC(requestCC);
            clserv.sendemail(requst.getCheque().getClient().getEmail(), "On vous confirme  que votre demande de carnet de cheque a été prise en compte pour le compte "+requst.getCheque().getNumcompte());

            return new ResponseEntity<RequestCC>(requst, HttpStatus.OK);
        }
        else 
//            throw new RequestException("compte introuvable");
            return new ResponseEntity<Long>(new Long(-1), HttpStatus.OK);

    }
	
	
	//Delete an existing request by id
	@DeleteMapping(value = "/{idRequest}/deleteRequest")
	public ResponseEntity<?> deleteRequest(@PathVariable Long idRequest) {
		
		Optional<RequestCC> requestCC= requestCCService.getRequestCCById(idRequest);
		
		
		if(requestCC.isPresent()) {
			requestCCService.deleteRequestCC(idRequest);
		return new ResponseEntity<RequestCC>(requestCC.get(),HttpStatus.OK);
		}
		else {
			throw new RequestException("requete introuvable");
		}
	}
	
	//For updating status of a request
		@PutMapping("/update/{idRequest}")
		public ResponseEntity<?> updateStatus( @PathVariable Long idRequest) {
			
			Optional<RequestCC> requst= requestCCService.getRequestCCById(idRequest);
			if(requst.isPresent()) {
			requst.get().setCloseRequest(true);
			RequestCC updatedRequest = requestCCService.updateRequestCC(requst.get());
			return new ResponseEntity<RequestCC>(updatedRequest, HttpStatus.OK);}
			else {
				return new ResponseEntity<String>("requette introiuvable",HttpStatus.NOT_FOUND);
			}
		}

	//get Request by her id
	@GetMapping(value = "/{idRequest}")
	public ResponseEntity<?> getRequestCCById(@PathVariable Long idRequest) {
		Optional<RequestCC> requestCC = requestCCService.getRequestCCById(idRequest);
		if(requestCC.isPresent()){
			return new ResponseEntity<RequestCC>(requestCC.get(), HttpStatus.OK);
		}
		else
		{
			throw new RequestException("Cette requete est introuvable veuillez essayer avec un autre ID, merci pour votre attention");		}
	}
	
	
	

	
	//for getting an activate request by account
	@GetMapping("/EnCoursdeTraitementbyCompte/{idCompte}")
	public List<RequestCC> findAllByCompte(@PathVariable Long idCompte) {
		Optional<compte_cheque> compteCheque = compteChequeService.getCompte_chequeById(idCompte);
;

		if(compteCheque.isPresent()){
			List<RequestCC> req = requestCCService.getRequestCCByCompteCheque(compteCheque.get());
			List<RequestCC> list = new LinkedList<>();
			 
			for (int i = 0; i<req.size() ;  i++) {
				if(req.get(i).getCloseRequest() == false) {	
				list.add(req.get(i));
				}
			}	
		return list;	
		}
		else
		{
			throw new RequestException("Compte introuvable");		}
	}
	
	
	
	//display finished requests by account
	@GetMapping("finisedbyCompte/{idCompte}")
	public List<RequestCC> finishedByCompte(@PathVariable Long idCompte) {
		Optional<compte_cheque> compteCheque = compteChequeService.getCompte_chequeById(idCompte);
;

		if(compteCheque.isPresent()){
			List<RequestCC> req = requestCCService.getRequestCCByCompteCheque(compteCheque.get());
			List<RequestCC> list = new LinkedList<>();
			 
			for (int i = 0; i<req.size() ;  i++) {
				if(req.get(i).getCloseRequest() == true) {	
				list.add(req.get(i));
				}
			}	
		return list;	
		}
		else
		{
			throw new RequestException("Compte introuvable");		}
	}
	
	
		@GetMapping(value = "/ByCompte/{idCompte}")
		public ResponseEntity<?> findRequestCarnetCByCompte(@PathVariable long idCompte) {
			Optional<compte_cheque> cl= compteChequeRepository.findById(idCompte);
			if(cl.isPresent()) {
			List<RequestCC> requestrv = requestCCService.getRequestCCByCompteCheque(cl.get());
				return new ResponseEntity<List<RequestCC>>(requestrv, HttpStatus.OK);
			}
			else {
				throw new RequestException("Compte indisponible");
			}
		}
		@GetMapping(value = "/ByClient/{idClient}")
		public ResponseEntity<?> findRequestCarnetCByClient(@PathVariable Long idClient) {
			Optional<Client> cl= clientRepo.findById(idClient);
			if(cl.isPresent()) {
			List<RequestCC> requestrv = requestCCService.gerRequestByClient(cl.get());

				return new ResponseEntity<List<RequestCC>>(requestrv, HttpStatus.OK);
			}
			else {
				throw new RequestException("Client No found");
			}
		}
	
}
