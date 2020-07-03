package com.helfarre.BankApi.Controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.helfarre.BankApi.Entities.Client;
import com.helfarre.BankApi.Entities.Role;
import com.helfarre.BankApi.Entities.compte_cheque;
import com.helfarre.BankApi.Entities.compte_epargne;
import com.helfarre.BankApi.Exceptions.ResourceNotFoundException;
import com.helfarre.BankApi.Repositories.ClientJpaRepository;
import com.helfarre.BankApi.Repositories.RoleRepo;
import com.helfarre.BankApi.Services.ClientService;
import com.helfarre.BankApi.Services.chequeService;
import com.helfarre.BankApi.Services.epargneService;

@RestController
@RequestMapping("/Client")

public class ClientManagementController {
	@Autowired
	private RoleRepo rolerepo;
	@Autowired
	private ClientService clientservice;	
	
	@Autowired
	private chequeService accountserv;	
	@Autowired
	private epargneService epargneserv;	
	
	@Autowired
	private ClientJpaRepository clientJpaRepositoryImpl;
	 
	@Autowired
	private PasswordEncoder passwordEncoder;
	  
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@GetMapping("/Clients")
	public List<Client> findAll() {
		return clientJpaRepositoryImpl.findAll();
	}
	
	@GetMapping("/updateAllPassword")
	public void updateAll() {
		
		List<Client> ll =  clientJpaRepositoryImpl.findAll();
		
		for(Client s : ll) {
			s.setPassword(passwordEncoder.encode(s.getPassword()));
			clientJpaRepositoryImpl.saveAndFlush(s);
		} 
	}
	
	@GetMapping("/cin/{cin}")
    public ResponseEntity<?> findClientBycin(@PathVariable String cin) {
        Optional<Client>cl= clientJpaRepositoryImpl.findByCin(cin);
        if(cl.isPresent()) {
            return new ResponseEntity<Client>(cl.get(),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<Long>(new Long(-1),HttpStatus.OK);
        }
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findClientById(@PathVariable Long id) {
		Optional<Client>cl= clientJpaRepositoryImpl.findById(id);
		if(cl.isPresent()) {
			return new ResponseEntity<Client>(cl.get(),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("id client introuvable",HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping(value = "/{id}/chequecomptes")
	public ResponseEntity<?> getAllClientcomptes(@PathVariable Long id) {
		Optional<Client> cl= clientservice.getClientById(id);
		if(cl.isPresent()) {
		Optional<List<compte_cheque>> Accounts = accountserv.getCompte_chequeByClient(cl.get());
		return new ResponseEntity<Optional<List<compte_cheque>>>(Accounts,HttpStatus.OK);
			}
		else
			return new ResponseEntity<Error>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping(value = "/{id}/epargnecomptes")
	public ResponseEntity<?> getAllClientAccount(@PathVariable Long id) {
		Optional<Client> cl= clientservice.getClientById(id);
		if(cl.isPresent()) {
		Optional<List<compte_epargne>> comptes = epargneserv.findByClient(id);
		return new ResponseEntity<Optional<List<compte_epargne>>>(comptes,HttpStatus.OK);
			}
		else
			return new ResponseEntity<Error>(HttpStatus.NO_CONTENT);
	}
	
	 //-Cr�er un client et le lier à une agence 
	 
	 @PostMapping("/addClient")
	    public ResponseEntity<?> createClient(@Valid @RequestBody Client theclient) throws ResourceNotFoundException {
   	  if( clientservice.findCin(theclient.getCin())) {
 		 System.out.println("asdasdasd");
   		  if( clientservice.findEmail(theclient.getEmail())) {
   			  Date date2 =new Date();
   			theclient.setJoiningDate(date2);
   			
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
   			
   			
   			theclient.setPassword(pass);
   			
   			Optional<Role> role=rolerepo.findById((long) 1);
   			theclient.setRole(role.get());
   			theclient.setPassword(passwordEncoder.encode(theclient.getPassword()));
 			    clientservice.saveOrUpdateClient(theclient);
 			    
 			   clientservice.sendemail(theclient.getEmail(), "On confirme l'ajout de vos informations \n"
 			    		+ "Nom:" +theclient.getFirstName()+ "\n Prenom:" +theclient.getLastName()+"\n Adresse:" +theclient.getAdresse()+"\n CIN:" +theclient.getCin()+"\n Phone:" +theclient.getPhone()+"\n Date de naissance:" +theclient.getDateNaissance()+"\n Email:" +theclient.getEmail()+"\n NB: Cet email sera votre identifiant pour se connecter sur application"+"\n Mot de passe:" +pass+"\n NB: Ce Mot de passe sera votre mot de passe pour la premiere utilisation pour se connecter sur l'application .Vous pouvez le changer à n'importe quel moment");
 				
   		  }
   		  
   		  else {
   			  //"le champ du Email est dupliquée , essayer de nouveau !"
   			 return new ResponseEntity<Long>(new Long(-1),HttpStatus.OK);

   		  }
   		  
   		   
   	   }
   	  else {
   		  //"le champ du numéro CIN est dupliquée , essayer de nouveau !"
   		  return new ResponseEntity<Long>(new Long(-2),HttpStatus.OK);

   	  }
   
	 return new ResponseEntity<Client>(theclient,HttpStatus.OK);
	 }
	 
	 @PutMapping("/updateClientEmail/{id}")
	   public ResponseEntity<?> updateEmail(@RequestBody String email, @PathVariable Long id){
		   Optional<Client> cln=clientJpaRepositoryImpl.findById(id);
		   if(cln.isPresent()) {
			   if(!clientJpaRepositoryImpl.findByEmail(email).isPresent()) {
		   
		   (cln.get()).setEmail(email);
		   Client clientUpdated=clientJpaRepositoryImpl.saveAndFlush(cln.get());
		   return new ResponseEntity<Client>(clientUpdated,HttpStatus.OK);
			   }
		   else {
			   return new ResponseEntity<String>("email dupliquee",HttpStatus.NOT_ACCEPTABLE);
		   }}else {
			   return new ResponseEntity<String>("client inexistant",HttpStatus.NOT_ACCEPTABLE);
		   }
	   }
	   
	
	   @PutMapping("/updateClientPassword/{id}")
	   public ResponseEntity<?> updatePassword(@RequestBody String pword, @PathVariable Long id){
		   Optional<Client> cln=clientJpaRepositoryImpl.findById(id);
		   if(cln.isPresent()) {
		   (cln.get()).setPassword(passwordEncoder.encode(pword));
		   Client clientUpdated=clientJpaRepositoryImpl.saveAndFlush(cln.get());
		   return new ResponseEntity<Client>(clientUpdated,HttpStatus.OK);
		   }else {
			   return new ResponseEntity<String>("client inexistant",HttpStatus.NOT_ACCEPTABLE);
		   }
	   }
	   
	   
	   @PutMapping("/updateClientPhone/{id}")
	   public ResponseEntity<?> updatePhone(@RequestBody String phone, @PathVariable Long id){
		   
		   Optional<Client> cln=clientJpaRepositoryImpl.findById(id);
		  if(cln.isPresent()) {
			 if( !clientJpaRepositoryImpl.findByPhone(phone).isPresent()) {
		   (cln.get()).setPhone(phone);
		   Client clientUpdated=clientJpaRepositoryImpl.saveAndFlush(cln.get());
		   return new ResponseEntity<Client>(clientUpdated,HttpStatus.OK);
		  } else {
			   return new ResponseEntity<String>("phone dupliquee",HttpStatus.NOT_ACCEPTABLE);
		   }}else {
			   return new ResponseEntity<String>("client inexistant",HttpStatus.NOT_ACCEPTABLE);
		   }
	   }
	   
	   @PutMapping("/updateClientCredentials/{id}")
	   public ResponseEntity<?> updateCredentaionas(@RequestBody Client clee, @PathVariable Long id){
		   Optional<Client> cln=clientJpaRepositoryImpl.findById(id);
		   if(cln.isPresent() ) {
			   if(!cln.get().getEmail().equals(clee.getEmail())){
			   if(!clientJpaRepositoryImpl.findByEmail(clee.getEmail()).isPresent()) {
		   (cln.get()).setAdresse(clee.getAdresse());
		   (cln.get()).setFirstName(clee.getFirstName());
		   (cln.get()).setLastName(clee.getLastName());
		   (cln.get()).setPhone(clee.getPhone());
		   (cln.get()).setEmail(clee.getEmail());
		   System.out.println();
		   System.out.println(cln.get().toString());
		   System.out.println();
		   Client clientUpdated=clientJpaRepositoryImpl.saveAndFlush(cln.get());
		   return new ResponseEntity<Client>(clientUpdated,HttpStatus.OK);
			   }
			   else
				   return new ResponseEntity<Integer>(-2,HttpStatus.OK);
			   }
			   else
			   {
				   (cln.get()).setAdresse(clee.getAdresse());
				   (cln.get()).setFirstName(clee.getFirstName());
				   (cln.get()).setLastName(clee.getLastName());
				   (cln.get()).setPhone(clee.getPhone());
				   (cln.get()).setEmail(clee.getEmail());
				   System.out.println();
				   System.out.println(cln.get().toString());
				   System.out.println();
				   Client clientUpdated=clientJpaRepositoryImpl.saveAndFlush(cln.get());

				   return new ResponseEntity<Client>(clientUpdated,HttpStatus.OK);
			   }
		   }
		   else {
			   return new ResponseEntity<Integer>(-1,HttpStatus.OK);
		   }
	   }
	   
	   @PutMapping("/updateClientAdresse/{id}")
	   public ResponseEntity<?> updateAdresse(@RequestBody String adresse, @PathVariable Long id){
		   Optional<Client> cln=clientJpaRepositoryImpl.findById(id);
		   if(cln.isPresent()) {
		   (cln.get()).setAdresse(adresse);
		   Client clientUpdated=clientJpaRepositoryImpl.saveAndFlush(cln.get());
		   return new ResponseEntity<Client>(clientUpdated,HttpStatus.OK);
		   }
		   else {
			   return new ResponseEntity<String>("client inexistant",HttpStatus.NOT_ACCEPTABLE);
		   }
	   }
	   @PutMapping("/verifyPassword/{id}")
	   public ResponseEntity<?> getpassword(@PathVariable Long id,@RequestBody String password){
		   Optional<Client> cln=clientJpaRepositoryImpl.findById(id);
		   if(cln.isPresent()) {
			   try {
				authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(cln.get().getEmail(),
						password));
				
				} catch(BadCredentialsException e)
				{
			   		  return new ResponseEntity<Integer>(-1,HttpStatus.OK);
					//throw new Exception("incorrect username or password",e);
				}
	   		   return new ResponseEntity<Integer>(1,HttpStatus.OK);

		   }
		   else {
			   return new ResponseEntity<Integer>(-1,HttpStatus.OK);
		   }
	   }
	   
	 /* 
	   
	   @PutMapping("/activateClient/{id}")
	   public ResponseEntity<?> activerClient(@PathVariable Long id){
		   Optional<Client> cln=clientJpaRepositoryImpl.findById(id);
		  if(cln.isPresent()) {
		   Client client =cln.get();
		   if(client.isIs_suspended() == true) {
			   client.setIs_suspended(false);
			   clientJpaRepositoryImpl.saveAndFlush(client);
			   return new ResponseEntity<>(false,HttpStatus.OK);
		   } 
		   return new ResponseEntity<>(true,HttpStatus.OK); }
		  else {
			  return new ResponseEntity<String>("client inexistant",HttpStatus.NOT_ACCEPTABLE);
		  }
	   }
	   
	   @PutMapping("/desactivateClient/{id}")
	   public ResponseEntity<?> desactiverClient(@PathVariable Long id){
		   Optional<Client> cln=clientJpaRepositoryImpl.findById(id);
		   if(cln.isPresent()) {
		   Client client =cln.get();
		   if(client.isIs_suspended() == false) {
			   client.setIs_suspended(true);
			   clientJpaRepositoryImpl.saveAndFlush(client);
			   return new ResponseEntity<>(true,HttpStatus.OK);
		   }
		return new ResponseEntity<>(false,HttpStatus.OK);  
	   } else {
			  return new ResponseEntity<String>("client inexistant",HttpStatus.NOT_ACCEPTABLE);
		  }
	 
	 
	   
	}
	   
	   */
	   
	   @PutMapping("/sendpassword/{email:.+}")
       public ResponseEntity<?> motdepasseoublie(@PathVariable String email){
		   System.out.println("worked");
		   System.out.println(email);
           Optional<Client> cln=clientJpaRepositoryImpl.findByEmail(email);
           if(cln.isPresent()) {
               Date date2=new Date();
               cln.get().setJoiningDate(date2);
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
                clientservice.sendemail(cln.get().getEmail(),"votre nouveau mot de passe est:   "+ pass);
           (cln.get()).setPassword(passwordEncoder.encode(pass));
           Client clientUpdated=clientJpaRepositoryImpl.saveAndFlush(cln.get());
           }
               return new ResponseEntity<Integer>(1,HttpStatus.OK);

       }
	   }



