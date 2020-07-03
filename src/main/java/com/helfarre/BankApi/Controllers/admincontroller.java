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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helfarre.BankApi.Entities.Role;
import com.helfarre.BankApi.Entities.admin;
import com.helfarre.BankApi.Entities.banquier;
import com.helfarre.BankApi.Exceptions.ResourceNotFoundException;
import com.helfarre.BankApi.Repositories.RoleRepo;
import com.helfarre.BankApi.Repositories.adminRepo;
import com.helfarre.BankApi.Services.ClientServiceImpl;
import com.helfarre.BankApi.Services.adminServ;

@RestController
@RequestMapping("/admin")
public class admincontroller {
@Autowired
private adminServ adminServ;

@Autowired
private ClientServiceImpl clserv;

@Autowired
private RoleRepo rolerepo;
@Autowired
private adminRepo adminrepo;
@Autowired
private PasswordEncoder passwordEncoder;
	
@GetMapping("/updateAllPassword")
public void updateAll() {
	
	List<admin> ll =  adminrepo.findAll();
	
	for(admin s : ll) {
		s.setPassword(passwordEncoder.encode(s.getPassword()));
		adminrepo.saveAndFlush(s);
	}
	 
}

	
	@GetMapping("/admins")
	public ResponseEntity<?> findAll() {
	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	if (principal instanceof UserDetails) {
		if(!adminrepo.findByEmail(((UserDetails)principal).getUsername()).isPresent()) {
			return new ResponseEntity<String>("error",HttpStatus.UNAUTHORIZED);
		}
		}
		return new ResponseEntity<List<admin>>(adminServ.findAll(),HttpStatus.OK);
	}
	@GetMapping("/admin/{id}")
	public ResponseEntity<?> findBanquierById(@PathVariable  Long id) throws ResourceNotFoundException{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			System.out.println(((UserDetails)principal).getUsername());
			if(!adminrepo.findByEmail(((UserDetails)principal).getUsername()).isPresent()) {
				return new ResponseEntity<String>("error",HttpStatus.UNAUTHORIZED);
			}
			}
		Optional<admin> bq = adminServ.findById(id);
		if(bq.isPresent()) 
		{
			return new ResponseEntity<admin>(bq.get(),HttpStatus.OK);
		}
		else
		{
			throw new ResourceNotFoundException("id admin introuvable");
		}
	}
		    
		    	    @PostMapping("/addadmin")
		    public ResponseEntity<?> addadmin(  @RequestBody admin thebanquier) throws ResourceNotFoundException {
		    	    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		    			if (principal instanceof UserDetails) {
		    				System.out.println(((UserDetails)principal).getUsername());
		    				if(!adminrepo.findByEmail(((UserDetails)principal).getUsername()).isPresent()) {
		    					return new ResponseEntity<String>("error",HttpStatus.UNAUTHORIZED);
		    				}
		    				}
	            	  if(adminServ.findCin(thebanquier.getCin())) {
	            		  if(adminServ.findEmail(thebanquier.getEmail())) {
	            			  Date date2 =new Date();
	          			     thebanquier.setJoiningDate(date2);
	          			   thebanquier.setJoiningDate(date2);
	          			 Optional<Role> role=rolerepo.findById((long) 3);
	          			thebanquier.setRole(role.get());
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
	    		   			
		    		   		 clserv.sendemail(thebanquier.getEmail(),"votre mot de passe est:   "+ pass);

	          			   thebanquier.setPassword(passwordEncoder.encode(pass));
	          			 //  thebanquier.setPassword(passwordEncoder.encode(thebanquier.getPassword()));
	   			            	adminServ.addadmin(thebanquier);
	            		  }
	            		  else {
	            			  throw new ResourceNotFoundException("le champ du Email est dupliquée , essayer à nouveau !");
	            		  }
	            		  
	            		   
	            	   }
	            	  else {
	            		  throw new ResourceNotFoundException("le champ du numéro CIN est dupliquée , essayer à nouveau !");
	            	  }
	            	 
  					return new ResponseEntity<admin>(thebanquier,HttpStatus.OK);

		    }
	 
		   @DeleteMapping("/admin/{adminId}")
		    public ResponseEntity<?> deleteBanquer(@PathVariable Long adminId) throws ResourceNotFoundException {
			   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (principal instanceof UserDetails) {
					System.out.println(((UserDetails)principal).getUsername());
					if(!adminrepo.findByEmail(((UserDetails)principal).getUsername()).isPresent()) {
						return new ResponseEntity<String>("error",HttpStatus.UNAUTHORIZED);
					}
					}
			   Optional<admin> tempBanquer = adminServ.findById(adminId);
				
				
				if (tempBanquer == null) {
					throw new ResourceNotFoundException("id banquier introuvable.");
				}
				
				adminrepo.deleteById(adminId);
				return ResponseEntity.ok().build();
				    
		    }

		   
}
