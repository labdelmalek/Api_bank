package com.helfarre.BankApi.Controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.helfarre.BankApi.Entities.Client;
import com.helfarre.BankApi.Entities.admin;
import com.helfarre.BankApi.Entities.banquier;
import com.helfarre.BankApi.Security.JwtUtil;
import com.helfarre.BankApi.Security.MyUserDetailsService;
import com.helfarre.BankApi.Security.RefreshToken;
import com.helfarre.BankApi.Security.RefreshTokenRepo;
import com.helfarre.BankApi.Security.RefreshTokenRequest;
import com.helfarre.BankApi.Security.jwtRequest;
import com.helfarre.BankApi.Services.BanquerService;
import com.helfarre.BankApi.Services.ClientService;
import com.helfarre.BankApi.Services.adminServ;

@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private MyUserDetailsService userDetailsService;
	@Autowired
	private ClientService userService;
	@Autowired
	private BanquerService banquierService;
	@Autowired
	private adminServ adminService;
	@Autowired
	private JwtUtil jwtTokenUtil;
	@Autowired
	private RefreshTokenRepo refreshRepo;
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody jwtRequest authenticationRequest) throws Exception {
		//faire l'authentification en utilsiation les donnes du formulaire login 
		//si le user name et le mot de passe sont correctes on renvoie le token, sinon on leve on exception de type
		//badcreadentielsexception
		HttpHeaders httpHeader = null;  
		try {
			System.out.println(authenticationRequest.getPassword());
		authenticationManager
		.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
				authenticationRequest.getPassword()));
		} catch(BadCredentialsException e)
		{
			return new ResponseEntity<Integer>(-1, httpHeader, HttpStatus.OK);
			//throw new Exception("incorrect username or password",e);
		}
		//si on arrive a ce stade les infos sont correctes donc on cree le token 
		//get userdetails by username
		
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getEmail());
		//creer le token
		final String token = jwtTokenUtil.generateToken(userDetails);
		Optional<Client> cl1=userService.findByEmail(authenticationRequest.getEmail());
		if (!cl1.isPresent()) {
			return new ResponseEntity<Integer>(-1, httpHeader, HttpStatus.OK);

		}
		// check if we already have a refresh token
		Optional<RefreshToken> refresher;
			refresher = refreshRepo.findByUserId("C-"+cl1.get().getId());
		   // Create the Header Object  
        httpHeader = new HttpHeaders(); 
		if(refresher.isPresent()) {
	        httpHeader.add("RefreshToken", refresher.get().getRefreshToken());
		}
		else
		{
			 // generate a refreshToken
	        UUID refreshToken = UUID.randomUUID();
	        httpHeader.add("RefreshToken", refreshToken.toString());
	        RefreshToken ref = new RefreshToken();
	        ref.setRefreshToken(refreshToken.toString());
	        if(cl1.isPresent())
	        	ref.setUserId("C-"+cl1.get().getId());   
	        refreshRepo.saveAndFlush(ref);
		}
		//envoyer le token dans la payload
        // Add token to the Header.  
        httpHeader.add("Authorization", token);
        	return new ResponseEntity<Long>(cl1.get().getId(), httpHeader, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/authenticateBanquier", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationTokenBq(@RequestBody jwtRequest authenticationRequest) throws Exception {
		//faire l'authentification en utilsiation les donnes du formulaire login 
		//si le user name et le mot de passe sont correctes on renvoie le token, sinon on leve on exception de type
		//badcreadentielsexception
		HttpHeaders httpHeader = null;  
		try {
		authenticationManager
		.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
				authenticationRequest.getPassword()));
		} catch(BadCredentialsException e)
		{
			return new ResponseEntity<Integer>(-1, httpHeader, HttpStatus.OK);
			//throw new Exception("incorrect username or password",e);
		}
		//si on arrive a ce stade les infos sont correctes donc on cree le token 
		//get userdetails by username
		
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getEmail());
		//creer le token
		
		final String token = jwtTokenUtil.generateToken(userDetails);
		Optional<banquier> cl2=banquierService.findByEmail(authenticationRequest.getEmail());
		if (!cl2.isPresent()) {
			return new ResponseEntity<Integer>(-1, httpHeader, HttpStatus.OK);

		}
		// check if we already have a refresh token
		Optional<RefreshToken> refresher;
			refresher = refreshRepo.findByUserId("B-"+cl2.get().getId());

		   // Create the Header Object  
        httpHeader = new HttpHeaders(); 
		if(refresher.isPresent()) {
	        httpHeader.add("RefreshToken", refresher.get().getRefreshToken());
		}
		else
		{
			 // generate a refreshToken
	        UUID refreshToken = UUID.randomUUID();
	        httpHeader.add("RefreshToken", refreshToken.toString());
	        RefreshToken ref = new RefreshToken();
	        ref.setRefreshToken(refreshToken.toString());

				ref.setUserId("B-"+cl2.get().getId());
	        
	        refreshRepo.saveAndFlush(ref);
		}
        
		//envoyer le token dans la payload
        // Add token to the Header.  
        httpHeader.add("Authorization", token);

        	return new ResponseEntity<Long>(cl2.get().getId(), httpHeader, HttpStatus.OK);
	}
	@RequestMapping(value = "/authenticateAdmin", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationTokenAdm(@RequestBody jwtRequest authenticationRequest) throws Exception {
		//faire l'authentification en utilsiation les donnes du formulaire login 
		//si le user name et le mot de passe sont correctes on renvoie le token, sinon on leve on exception de type
		//badcreadentielsexception
		HttpHeaders httpHeader = null;  
		try {
		authenticationManager
		.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
				authenticationRequest.getPassword()));
		} catch(BadCredentialsException e)
		{
			return new ResponseEntity<Integer>(-1, httpHeader, HttpStatus.OK);
			//throw new Exception("incorrect username or password",e);
		}
		//si on arrive a ce stade les infos sont correctes donc on cree le token 
		//get userdetails by username
		
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getEmail());
		//creer le token
		
		final String token = jwtTokenUtil.generateToken(userDetails);
		Optional<admin> cl2=adminService.findByEmail(authenticationRequest.getEmail());
		if (!cl2.isPresent()) {
			return new ResponseEntity<Integer>(-1, httpHeader, HttpStatus.OK);
		}
		// check if we already have a refresh token
		Optional<RefreshToken> refresher;
			refresher = refreshRepo.findByUserId("A-"+cl2.get().getId());

		   // Create the Header Object  
        httpHeader = new HttpHeaders(); 
		if(refresher.isPresent()) {
	        httpHeader.add("RefreshToken", refresher.get().getRefreshToken());
		}
		else
		{
			 // generate a refreshToken
	        UUID refreshToken = UUID.randomUUID();
	        httpHeader.add("RefreshToken", refreshToken.toString());
	        RefreshToken ref = new RefreshToken();
	        ref.setRefreshToken(refreshToken.toString());

				ref.setUserId("A-"+cl2.get().getId());
	        
	        refreshRepo.saveAndFlush(ref);
		}
        
		//envoyer le token dans la payload
        // Add token to the Header.  
        httpHeader.add("Authorization", token);

        	return new ResponseEntity<Long>(cl2.get().getId(), httpHeader, HttpStatus.OK);
	}

	@PostMapping("/renewBanquier")
    public ResponseEntity<?> renewbq(@RequestBody RefreshTokenRequest data) {
        String userId = data.getUserId();
        
        String refreshToken = data.getRefreshToken();
        
        if (refreshToken == null) {
    		return new ResponseEntity<Integer>(0,HttpStatus.OK);
        }

        // check if the refreshToken is for this username
        Optional<RefreshToken> ref = refreshRepo.findByToken(refreshToken);
        
        if (ref.isPresent() && ref.get().getUserId().equals("B-"+userId)) {
            // generate a new access token for this user
        	Optional<banquier> client = banquierService.findById(Long.parseLong(userId));
        	final UserDetails userDetails = userDetailsService.
    				loadUserByUsername(client.get().getEmail());
    		final String token = jwtTokenUtil.generateToken(userDetails);
    		//creer le token
    		 HttpHeaders httpHeader = new HttpHeaders();
   
    	     httpHeader.add("Authorization", token);
    	     RefreshToken tq = new RefreshToken();
    	     tq.setRefreshToken(token);
    		return new ResponseEntity<RefreshToken>(tq,httpHeader,HttpStatus.OK);
        }

		return new ResponseEntity<Integer>(0,HttpStatus.OK);

	}
	@PostMapping("/renewAdmin")
    public ResponseEntity<?> renewadm(@RequestBody RefreshTokenRequest data) {
        String userId = data.getUserId();
        
        String refreshToken = data.getRefreshToken();
        
        if (refreshToken == null) {
    		return new ResponseEntity<Integer>(0,HttpStatus.OK);
        }

        // check if the refreshToken is for this username
        Optional<RefreshToken> ref = refreshRepo.findByToken(refreshToken);
        
        if (ref.isPresent() && ref.get().getUserId().equals("A-"+userId)) {
            // generate a new access token for this user
        	Optional<admin> client = adminService.findById(Long.parseLong(userId));
        	final UserDetails userDetails = userDetailsService.
    				loadUserByUsername(client.get().getEmail());
    		final String token = jwtTokenUtil.generateToken(userDetails);
    		//creer le token
    		 HttpHeaders httpHeader = new HttpHeaders();
   
    	     httpHeader.add("Authorization", token);
    	     RefreshToken tq = new RefreshToken();
    	     tq.setRefreshToken(token);
    		return new ResponseEntity<RefreshToken>(tq,httpHeader,HttpStatus.OK);
        }

		return new ResponseEntity<Integer>(0,HttpStatus.OK);

	}
	@PostMapping("/renewClient")
    public ResponseEntity<?> renew(@RequestBody RefreshTokenRequest data) {
        String userId = data.getUserId();
        
        String refreshToken = data.getRefreshToken();
        
        if (refreshToken == null) {
    		return new ResponseEntity<Integer>(0,HttpStatus.OK);
        }

        // check if the refreshToken is for this username
        Optional<RefreshToken> ref = refreshRepo.findByToken(refreshToken);
        
        if (ref.isPresent() && ref.get().getUserId().equals("C-"+userId)) {
            // generate a new access token for this user
        	Optional<Client> client = userService.getClientById(Long.parseLong(userId));
        	final UserDetails userDetails = userDetailsService.
    				loadUserByUsername(client.get().getEmail());
    		final String token = jwtTokenUtil.generateToken(userDetails);
    		//creer le token
    		 HttpHeaders httpHeader = new HttpHeaders();
   
    	     httpHeader.add("Authorization", token);
    	     RefreshToken tq = new RefreshToken();
    	     tq.setRefreshToken(token);
    		return new ResponseEntity<RefreshToken>(tq,httpHeader,HttpStatus.OK);
        }

		return new ResponseEntity<Integer>(0,HttpStatus.OK);

	}
	@RequestMapping(value = "/logoutcl/{idclient}", method = RequestMethod.GET)
	public ResponseEntity<?> LogoutClient(@PathVariable Long idclient) throws Exception {
		System.out.println("logdasdasdasdasdasdasdasdasdout");
		System.out.println();
		Optional<RefreshToken> s = this.refreshRepo.findByUserId("C-"+idclient);
		if (s.isPresent()) {
			this.refreshRepo.delete(s.get());
			System.out.println("logout");
			return new ResponseEntity<RefreshToken> (s.get(),HttpStatus.OK);
		}
		System.out.println("logout1");

		return new ResponseEntity<Integer> (-1,HttpStatus.OK);

	}
	@RequestMapping(value = "/logoutadm/{idadmin}", method = RequestMethod.GET)
	public ResponseEntity<?> LogoutAdmin(@PathVariable Long idadmin) throws Exception {
		System.out.println();
		Optional<RefreshToken> s = this.refreshRepo.findByUserId("A-"+idadmin);
		if (s.isPresent()) {
			this.refreshRepo.delete(s.get());
			System.out.println("logout");
			return new ResponseEntity<RefreshToken> (s.get(),HttpStatus.OK);
		}
		System.out.println("logout1");

		return new ResponseEntity<Integer> (-1,HttpStatus.OK);

	}
	@RequestMapping(value = "/logoutbq/{idbanquier}", method = RequestMethod.GET)
	public ResponseEntity<?> LogoutBanquier(@PathVariable Long idbanquier) throws Exception {
		System.out.println();
		Optional<RefreshToken> s = this.refreshRepo.findByUserId("B-"+idbanquier);
		if (s.isPresent()) {
			this.refreshRepo.delete(s.get());
			System.out.println("logout");
			return new ResponseEntity<RefreshToken> (s.get(),HttpStatus.OK);
		}
		System.out.println("logout1");

		return new ResponseEntity<Integer> (-1,HttpStatus.OK);

	}

}
