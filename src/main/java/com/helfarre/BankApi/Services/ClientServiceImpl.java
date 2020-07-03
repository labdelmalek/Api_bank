package com.helfarre.BankApi.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.helfarre.BankApi.Entities.Client;
import com.helfarre.BankApi.Repositories.ClientJpaRepository;
@Service

public class ClientServiceImpl implements ClientService {

	  @Autowired
private ClientJpaRepository clientrepo;
	  @Autowired
	  private JavaMailSender javaMailSender;

	  
	@Override
	public Optional<Client> getClientById(Long id) {
		Optional<Client> clients = clientrepo.findById(id);
		return clients;		
	}
	
	@Override
	public void saveOrUpdateClient(Client client) {
		clientrepo.save(client);
	}

	@Override
	public void deleteClient(Long id) {
		clientrepo.deleteById(id);
	}

	@Override
	public List<Client> getAllClients() {
		List<Client> clients = clientrepo.findAll();
		if (clients.isEmpty())
			return null;
		return clients;
	}


	


	@Override
	public Optional<Client> findByEmail(String email) {
		return clientrepo.findByEmail(email);
	}


	@Override
	public Optional<Client> findByCin(String cin) {
		
		return clientrepo.findByCin(cin); 
	}



	


	@Override
	public boolean findCin(String ci) {
		List<String> cin =clientrepo.findCin();
		for(String c :cin) {
			if(c.equals(ci))
				return false;
			
		}
		return true;
	}



	@Override
	public boolean findEmail(String em) {
		
		List<String> email =clientrepo.findEmail();
		for(String c :email) {
			if(c.equals(em))
				return false;
			
		}
		return true;
	}
	
	@Override
	
	public boolean sendemail(String mail,String message) {
	try {	

     

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail);
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("Bonjour Mr, \n"+message+"\n Merci pour votre confiance");
        javaMailSender.send(mailMessage);
return true;
	}catch(Exception e) {
		return false;
	}




}}
