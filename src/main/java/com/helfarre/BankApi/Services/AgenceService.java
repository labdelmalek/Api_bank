package com.helfarre.BankApi.Services;

import java.util.List;
import java.util.Optional;

import com.helfarre.BankApi.Entities.Agence;




public interface AgenceService {

	public List<Agence> findAll();
	
	public Agence findById(Long theId);
	
	public void addAgence(Agence agence);


	public void deleteById(Long theId);
	
	public Agence findByBanqs_Id(Long banquierId);
	
	public Boolean existsById(Long id);
	
	public Agence updateAgence(Agence agence);
	

	
	Optional<Agence> findByCompteId(Long id);

	Agence addagence(Agence ag);

	
	
}
