package com.helfarre.BankApi.Controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.helfarre.BankApi.Entities.Role;
import com.helfarre.BankApi.Entities.banquier;
import com.helfarre.BankApi.Exceptions.ResourceNotFoundException;
import com.helfarre.BankApi.Repositories.BanquierJpaRepository;
import com.helfarre.BankApi.Repositories.RoleRepo;
import com.helfarre.BankApi.Repositories.adminRepo;
import com.helfarre.BankApi.Services.AgenceService;
import com.helfarre.BankApi.Services.BanquerService;
import com.helfarre.BankApi.Services.ClientService;



@RestController
@RequestMapping("/banker")
public class BanquerRestController {

private BanquerService banquerService;

@Autowired
private AgenceService agenceService;

@Autowired
private ClientService clserv;

@Autowired
private RoleRepo rolerepo;
@Autowired
private BanquierJpaRepository banquierJpaRepositoryImpl;
@Autowired
private PasswordEncoder passwordEncoder;
@Autowired
private adminRepo adminrepo;
	@Autowired
	public BanquerRestController(BanquerService thebanquerService ) {
		banquerService = thebanquerService;
	}
	
	@GetMapping("/bankers")
	public List<banquier> findAll() {
		return banquerService.findAll();
	}
	@GetMapping("/updateAllPassword")
	public void updateAll() {
		
		List<banquier> ll =  banquierJpaRepositoryImpl.findAll();
		
		for(banquier s : ll) {
			s.setPassword(passwordEncoder.encode(s.getPassword()));
			banquierJpaRepositoryImpl.saveAndFlush(s);
		}
		 
	}
	
	@GetMapping("/bankers/{id}")
	public ResponseEntity<?> findBanquierById(@PathVariable (value = "id") Long id) throws ResourceNotFoundException{
		Optional<banquier> bq = banquerService.findById(id);
		if(bq.isPresent()) 
		{
			return new ResponseEntity<banquier>(bq.get(),HttpStatus.OK);
		}
		else
		{
			throw new ResourceNotFoundException("id banquier introuvable");
		}
	}
	
	 @GetMapping("/agencies/banquers/{agencyId}")
	 public ResponseEntity<?> getBanquiersByAgence(@PathVariable (value = "agencyId") Long agencyId) throws ResourceNotFoundException {

         List<banquier> bq=banquerService.findByAgencyId(agencyId);
         if(agenceService.existsById(agencyId)) {
             return new ResponseEntity<List<banquier>>(bq,HttpStatus.OK);
         }else
         {
             throw new ResourceNotFoundException("id agence introuvable");
         }

  }
		   @PostMapping("/addBanker/{agencyId}")
		    public ResponseEntity<?> addBanquier(@PathVariable Long agencyId,  @RequestBody banquier thebanquier) throws ResourceNotFoundException {
			   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (principal instanceof UserDetails) {
					if(!adminrepo.findByEmail(((UserDetails)principal).getUsername()).isPresent()) {
						return new ResponseEntity<String>("error",HttpStatus.UNAUTHORIZED);
					}
					}
 			     if(agenceService.existsById(agencyId)) {
	            	  if(banquerService.findCin(thebanquier.getCin())) {
	            		  if(banquerService.findEmail(thebanquier.getEmail())) {
	            	   			Optional<Role> role=rolerepo.findById((long) 2);

	            			  thebanquier.setRole(role.get());
		            			 banquier test= banquerService.addbanquier(thebanquier,agencyId) ;
		                         return new ResponseEntity<banquier>(test,HttpStatus.OK);

	            		  }
	            		  else {
	            			  throw new ResourceNotFoundException("le champ du Email est dupliquée , essayer à nouveau !");
	            		  }
	            		  
	            		   
	            	   }
	            	  else {
	            		  throw new ResourceNotFoundException("le champ du numéro CIN est dupliquée , essayer à nouveau !");
	            	  }
	            	 
	               }
	               else {
	            	   throw new ResourceNotFoundException("id agence introuvable.");
	               }
		    }
	    

		    		   @PutMapping("/updatebankerPassword/{id}/{pword}")
		    		   public ResponseEntity<?> updatePassword(@PathVariable String pword, @PathVariable Long id){
		    			   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		    				if (principal instanceof UserDetails) {
		    					if(!adminrepo.findByEmail(((UserDetails)principal).getUsername()).isPresent()) {
		    						return new ResponseEntity<String>("error",HttpStatus.UNAUTHORIZED);
		    					}
		    					}
		    			   Optional<banquier> cln=banquerService.findById(id);
		    			   if(cln.isPresent()) {
		    			   (cln.get()).setPassword(passwordEncoder.encode(pword));
		    			   banquier BanquerUpdated=banquerService.updateBanquier(cln.get());
		    			   return new ResponseEntity<banquier>(BanquerUpdated,HttpStatus.OK);
		    			   }else {
		    				   return new ResponseEntity<String>("client inexistant",HttpStatus.NOT_ACCEPTABLE);
		    			   }
		    		   }

	    @PutMapping("/updateBanquierEmail/{id}")
		   public ResponseEntity<?> updateEmail(@RequestBody String email, @PathVariable Long id){
	    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    	if (principal instanceof UserDetails) {
	    		if(!adminrepo.findByEmail(((UserDetails)principal).getUsername()).isPresent()) {
	    			return new ResponseEntity<String>("error",HttpStatus.UNAUTHORIZED);
	    		}
	    		}
			   Optional<banquier> cln=banquerService.findById(id);
			   if(cln.isPresent()) {
				   if(!banquierJpaRepositoryImpl.findByEmail(email).isPresent()) {
			   
			   cln.get().setEmail(email);
			   banquier BanquerUpdated=banquerService.updateBanquier(cln.get());
			   return new ResponseEntity<banquier>(BanquerUpdated,HttpStatus.OK);
				   }
		    else {
			   return new  ResponseEntity<String>("non modifié (email dupliqué)",HttpStatus.NOT_FOUND);
			   }
			   
		   }return new  ResponseEntity<String>("non modifié (id introuvable)",HttpStatus.NOT_FOUND);
		   }
		
	 
		   @PutMapping("/updateBanquierPhone/{id}")
		   public ResponseEntity<?> updatePhone(@RequestBody String phone, @PathVariable Long id){
			   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (principal instanceof UserDetails) {
					if(!adminrepo.findByEmail(((UserDetails)principal).getUsername()).isPresent()) {
						return new ResponseEntity<String>("error",HttpStatus.UNAUTHORIZED);
					}
					}
			   Optional<banquier> cln=banquerService.findById(id);
			   if(cln.isPresent()) {
				   if(!banquierJpaRepositoryImpl.findByPhone(phone).isPresent()) {
			   
			   cln.get().setPhone(phone);
			   banquier BanquerUpdated=banquerService.updateBanquier(cln.get());
			   return new ResponseEntity<banquier>(BanquerUpdated,HttpStatus.OK);
			   
		   }else {
			   return new  ResponseEntity<String>("non modifié (phone dupliqué)",HttpStatus.NOT_FOUND);
			   }
			   
		   }return new  ResponseEntity<String>("non modifié (id introuvable)",HttpStatus.NOT_FOUND);
		   }
	 
		   
		   
		   @PutMapping("/updateBanquierAdresse/{id}")
		   public ResponseEntity<?> updateAdresse(@RequestBody String adresse, @PathVariable Long id){
			   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (principal instanceof UserDetails) {
					if(!adminrepo.findByEmail(((UserDetails)principal).getUsername()).isPresent()) {
						return new ResponseEntity<String>("error",HttpStatus.UNAUTHORIZED);
					}
					}
			   Optional<banquier> cln=banquerService.findById(id);
			   cln.get().setAdresse(adresse);
			   banquier BanquerUpdated=banquerService.updateBanquier(cln.get());
			   return new ResponseEntity<banquier>(BanquerUpdated,HttpStatus.OK);
			   
		   }
	 
		   @DeleteMapping("/banquiers/{banquierId}")
		    public ResponseEntity<?> deleteBanquer(@PathVariable Long banquierId) throws ResourceNotFoundException {
			   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (principal instanceof UserDetails) {
					if(!adminrepo.findByEmail(((UserDetails)principal).getUsername()).isPresent()) {
						return new ResponseEntity<String>("error",HttpStatus.UNAUTHORIZED);
					}
					}
			   Optional<banquier> tempBanquer = banquierJpaRepositoryImpl.findById(banquierId);
				
				
				if (tempBanquer == null) {
					throw new ResourceNotFoundException("id banquier introuvable.");
				}
				
				banquierJpaRepositoryImpl.deleteById(banquierId);
				return ResponseEntity.ok().build();
				    
		    }

		   
		   
		   @PutMapping("/sendpassword/{id}")
	       public ResponseEntity<?> motdepasseoublié( @PathVariable Long id){
				Optional<banquier> bq = banquerService.findById(id);
	           if(bq.isPresent()) {
	              
	               //    cln.get().setIs_suspended(false);
	                   int leftLimit = 97;
	                   int rightLimit = 122; 
	                   int targetStringLength = 20;
	                   Random random = new Random();
	                   StringBuilder buffer = new StringBuilder(targetStringLength);
	                   for (int i = 0; i < targetStringLength; i++) {
	                       int randomLimitedInt = leftLimit + (int) 
	                         (random.nextFloat() * (rightLimit - leftLimit + 1));
	                       buffer.append((char) randomLimitedInt);
	                   }
	                   String pass = buffer.toString();

	                clserv.sendemail(bq.get().getEmail(),"votre nouveau mot de passe est:   "+ pass);

	           (bq.get()).setPassword(passwordEncoder.encode(pass));
	           banquierJpaRepositoryImpl.save(bq.get());
	           }

	               return new ResponseEntity<Integer>(1,HttpStatus.OK);

	       }
		   
		   
		   
}










