package com.helfarre.BankApi.Services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helfarre.BankApi.Entities.Agence;
import com.helfarre.BankApi.Entities.compte;
import com.helfarre.BankApi.Repositories.AgenceJpaRepository;
import com.helfarre.BankApi.Repositories.compteRepository;



@Service
public class AgenceServiceImpl implements AgenceService {


	@Autowired
	private AgenceJpaRepository agencejpa;
	
	@Autowired 
	private compteRepository cprepo;
	
	
	
	private AgenceJpaRepository agenceRepository;
	
	@Autowired
	public AgenceServiceImpl(AgenceJpaRepository theagenceRepository) {
	        agenceRepository = theagenceRepository;
	}
	
	@Override
	public List<Agence> findAll() {
		return agenceRepository.findAll();
	}
	
	
	@Override
	public Agence findById(Long theId) {
		Optional<Agence> result = agenceRepository.findById(theId);
		
		Agence theAgence = null;
		
		if (result.isPresent()) {
			theAgence = result.get();
		}
		else {
			
			throw new RuntimeException("Did not find  agency id - " + theId);
		}
		
		return theAgence;
	}
	
	
	@Override
	public void addAgence(Agence agence) {
		agenceRepository.save(agence);
		
	}

	

	@Override
	public void deleteById(Long theId) {
		
		agenceRepository.deleteById(theId);
			
		
		
	}

	@Override
	public Agence findByBanqs_Id(Long banquierId) {
		 Agence result =agenceRepository.findByBanqs_Id(banquierId);
			
			return result;
	}

	@Override
	public Boolean existsById(Long id) {
		
		return agenceRepository.existsById(id);
	}

	@Override
	public Agence updateAgence(Agence agence) {
		
		return agenceRepository.saveAndFlush(agence);
	}

	@Override
	public Optional<Agence> findByCompteId(Long id) {
		Optional<Agence> result =agenceRepository.findByClis_Id(id);
		
		return result;
	}

	@Override
public Agence addagence(Agence ag) {
	 
    String var = agencejpa.getnumer();
    while(var.length() < 8) {
         var=0+var;
    }
    ag.setNumagence(var);
  
    agencejpa.test();
   addAgence(ag);
    compte cp=new compte();
    cp.setBalance(100000000);
    cp.setNumcompte("agence"+ag.getId());
    cp.setTypecompte("banque");
    cp.setNuminternational("agence"+ag.getId());
    cp.setLagence(ag);
    cp.setIs_suspended(false);
    cp.setRib("agence");
    Date date=new Date();
    cp.setCreation_date(date);
    
    cprepo.saveAndFlush(cp);
    return ag;
}


	

	

}






