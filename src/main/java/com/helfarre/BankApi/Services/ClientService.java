package com.helfarre.BankApi.Services;

import java.util.List;
import java.util.Optional;

import com.helfarre.BankApi.Entities.Client;

public interface ClientService {
	List<Client> getAllClients() ;
	Optional<Client> getClientById(Long id) ;
	

	
	Optional<Client> findByEmail(String email);

	void saveOrUpdateClient(Client Client) ;
	
	Optional<Client> findByCin(String cin);
	
	
	
	
	
	
	
	void deleteClient(Long id);
	

	public boolean findCin(String ci);
	public boolean findEmail(String em);
	boolean sendemail(String mail, String message);

}