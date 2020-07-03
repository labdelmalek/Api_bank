package com.helfarre.BankApi.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.helfarre.BankApi.Exceptions.ResourceNotFoundException;
import com.helfarre.BankApi.Repositories.AgenceJpaRepository;
import com.helfarre.BankApi.Repositories.adminRepo;
import com.helfarre.BankApi.Repositories.compteRepository;
import com.helfarre.BankApi.Services.AgenceService;
import com.helfarre.BankApi.Services.BanquerService;





@RestController
@RequestMapping("/api")
public class AgenceRestController {

private AgenceService agenceService;

	@Autowired
	private BanquerService banquerService;
	@Autowired
	private AgenceJpaRepository agencejpa;
	
	@Autowired 
	private compteRepository cprepo;
	@Autowired
	private adminRepo adminrepo;
	@Autowired
	public AgenceRestController(AgenceService theagenceService) {
		agenceService = theagenceService;
	}
	
	
	@GetMapping("/agencies")
	public ResponseEntity<?> findAll() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			if(!adminrepo.findByEmail(((UserDetails)principal).getUsername()).isPresent()) {
				return new ResponseEntity<String>("error",HttpStatus.UNAUTHORIZED);
			}
			}
		return new ResponseEntity<List<Agence>>(agenceService.findAll(),HttpStatus.OK);
	}
	
	@GetMapping("/agencies/{agencyId}")
	public ResponseEntity<?> getAgence(@PathVariable Long agencyId) throws ResourceNotFoundException {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			if(!adminrepo.findByEmail(((UserDetails)principal).getUsername()).isPresent()) {
				return new ResponseEntity<String>("error",HttpStatus.UNAUTHORIZED);
			}
			}
		if(agenceService.existsById(agencyId)) {
		Agence theAgence = agenceService.findById(agencyId);
		return new ResponseEntity<Agence>(theAgence,HttpStatus.OK);		
		}
		else {
			throw new ResourceNotFoundException("id agence introuvable");
			
		}
				
	}

	 @GetMapping("/agences/comptes/{idcompte}")
	    public Optional<Agence> getAgenceByCompte(@PathVariable Long idcompte) throws ResourceNotFoundException {
		 
		 if(cprepo.existsById(idcompte)) {
		        return agenceService.findByCompteId(idcompte);
			 }
			 else 
			 {
				 throw new ResourceNotFoundException("id compte introuvable.");
			 }
	      	
	 }
	 
	 @GetMapping("/agencies/banquiers/banquier/{banquierId}")
	    public ResponseEntity<?> getAgenceByBanquier(@PathVariable Long banquierId) throws ResourceNotFoundException {
		 
		 if(banquerService.existsById(banquierId)) {
	        Agence ag= agenceService.findByBanqs_Id(banquierId);
	        return new ResponseEntity<Agence>(ag,HttpStatus.OK);
		 }
		 else 
		 {
			 throw new ResourceNotFoundException("id banquier introuvable .");
		 }
	 }
	 
	 
	  
	 @PostMapping("/addagence")
     public ResponseEntity<?> addAgence(@RequestBody   Agence agence) throws ResourceNotFoundException {
		 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetails) {
				if(!adminrepo.findByEmail(((UserDetails)principal).getUsername()).isPresent()) {
					return new ResponseEntity<String>("error",HttpStatus.UNAUTHORIZED);
				}
				}
        if(!agencejpa.findByFax(agence.getFax()).isPresent()) {
                if(!agencejpa.findByEmail(agence.getEmail()).isPresent()) {
                   if(!agencejpa.findByPhone(agence.getPhone()).isPresent()) {
Agence ag=agenceService.addagence(agence);
    //   return "confirmationagence";
  
        return  new ResponseEntity<Agence>(ag,HttpStatus.OK);

    }else
     {
         throw new ResourceNotFoundException("phone dupliqué");
     }}else
     {
         throw new ResourceNotFoundException("email dupliqué");
     }}else
     {
         throw new ResourceNotFoundException("fax dupliqué");
     }}




	   
	   @DeleteMapping("/deleteagence/{agencyId}")
	    public ResponseEntity<?> deleteAgence(@PathVariable Long agencyId) {
		   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetails) {
				if(!adminrepo.findByEmail(((UserDetails)principal).getUsername()).isPresent()) {
					return new ResponseEntity<String>("error",HttpStatus.UNAUTHORIZED);
				}
				}
		   Agence tempAgence = agenceService.findById(agencyId);
			
			
			if (tempAgence == null) {
				throw new RuntimeException("id agence introuvable");
			}
			
			agenceService.deleteById(agencyId);
			return ResponseEntity.ok().build();
			
	       
	    }
	   
	   @PutMapping("/updateAgencyName/{id}")
	   
	   public ResponseEntity<?> updateName(@RequestBody Agence agence, @PathVariable Long id){
		   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetails) {
				if(!adminrepo.findByEmail(((UserDetails)principal).getUsername()).isPresent()) {
					return new ResponseEntity<String>("error",HttpStatus.UNAUTHORIZED);
				}
				}
		   Agence ag=agenceService.findById(id);
		   if(ag!=null) {
		   ag.setName(agence.getName());
		   Agence agenceUpdated=agenceService.updateAgence(ag);
		   return new ResponseEntity<Agence>(agenceUpdated,HttpStatus.OK);}
		   return new  ResponseEntity<String>("non modifié (id introuvable)",HttpStatus.NO_CONTENT);
		   
	   }
	 

	
	   
	   @PutMapping("/updateAgencyEmail/{id}")
	   public ResponseEntity<?> updateEmail(@RequestBody Agence agence, @PathVariable Long id){
		   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetails) {
				if(!adminrepo.findByEmail(((UserDetails)principal).getUsername()).isPresent()) {
					return new ResponseEntity<String>("error",HttpStatus.UNAUTHORIZED);
				}
				}
		   Agence ag=agenceService.findById(id);
		   if(ag!=null) {
			   
		   if(!agencejpa.findByEmail(agence.getEmail()).isPresent()) {
		   ag.setEmail(agence.getEmail());
		   Agence agenceUpdated=agenceService.updateAgence(ag);
		   return new ResponseEntity<Agence>(agenceUpdated,HttpStatus.OK);
		   }
		   else 
		   {
		   return new  ResponseEntity<String>("non modifié (email dupliqué)",HttpStatus.NOT_FOUND);
		   }
		   
	   }return new  ResponseEntity<String>("non modifié (id introuvable)",HttpStatus.NOT_FOUND);
	   }
	   @PutMapping("/updateAgencyPhone/{id}")
	   public ResponseEntity<?> updatePhone(@RequestBody Agence agence, @PathVariable Long id){
		   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetails) {
				if(!adminrepo.findByEmail(((UserDetails)principal).getUsername()).isPresent()) {
					return new ResponseEntity<String>("error",HttpStatus.UNAUTHORIZED);
				}
				}
		   Agence ag=agenceService.findById(id);
		   if(ag!=null) {
			   if(!agencejpa.findByPhone(agence.getPhone()).isPresent()) {
		   ag.setPhone(agence.getPhone());
		   Agence agenceUpdated=agenceService.updateAgence(ag);
		   return new ResponseEntity<Agence>(agenceUpdated,HttpStatus.OK);}
		   else {
			   return new  ResponseEntity<String>("non modifié (PHONE dupliqué)",HttpStatus.NOT_FOUND);
			   }
		   }return new  ResponseEntity<String>("non modifié (id introuvable)",HttpStatus.NOT_FOUND);
	   }
	   
	   @PutMapping("/updateAgencyFax/{id}")
	   public ResponseEntity<?> updateFax(@RequestBody Agence agence, @PathVariable Long id){
		   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetails) {
				if(!adminrepo.findByEmail(((UserDetails)principal).getUsername()).isPresent()) {
					return new ResponseEntity<String>("error",HttpStatus.UNAUTHORIZED);
				}
				}
		   Agence ag=agenceService.findById(id);
		   if(ag!=null) {
			   if(!agencejpa.findByFax(agence.getFax()).isPresent()) {
		   ag.setFax(agence.getFax());
		   Agence agenceUpdated=agenceService.updateAgence(ag);
		   return new ResponseEntity<Agence>(agenceUpdated,HttpStatus.OK);}
			   else {
				   return new  ResponseEntity<String>("non modifié (Fax dupliqué)",HttpStatus.NOT_FOUND);
				   }
			   }return new  ResponseEntity<String>("non modifié (id introuvable)",HttpStatus.NOT_FOUND);
		   }
		
}