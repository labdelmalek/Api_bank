package com.helfarre.BankApi.Security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.helfarre.BankApi.Entities.Client;
import com.helfarre.BankApi.Entities.admin;
import com.helfarre.BankApi.Entities.banquier;
import com.helfarre.BankApi.Repositories.BanquierJpaRepository;
import com.helfarre.BankApi.Repositories.ClientJpaRepository;
import com.helfarre.BankApi.Repositories.adminRepo;


@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private ClientJpaRepository clientRepo;
	@Autowired
	private BanquierJpaRepository banquierRepo;
	@Autowired
	private adminRepo adminResposi;
	@Override
	//Locates the user based on the username.  
	// return UserDetails
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Client> client = clientRepo.findByEmail(username);
		Optional<banquier> bq = banquierRepo.findByEmail(username);
		Optional<admin> adm = adminResposi.findByEmail(username);

		if(client.isPresent())
			return client.map(MyUserDetails::new).get();
		else if(adm.isPresent()) {
			return adm.map(MyUserDetails::new).get();
		}
		else 
		{
		bq.orElseThrow(() -> new UsernameNotFoundException("NotFound " + username));
		return bq.map(MyUserDetails::new).get();

		}
		
	}

}
