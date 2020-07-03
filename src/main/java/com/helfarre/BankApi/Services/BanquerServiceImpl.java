package com.helfarre.BankApi.Services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.helfarre.BankApi.Entities.banquier;
import com.helfarre.BankApi.Repositories.BanquierJpaRepository;


@Service
public class BanquerServiceImpl implements BanquerService {
@Autowired
	private BanquerService banquerService;

	@Autowired
	private ClientServiceImpl clserv;
	@Autowired
	private AgenceService agenceService;

	@Autowired
	private BanquierJpaRepository banquierJpaRepositoryImpl;
	@Autowired
	private PasswordEncoder passwordEncoder;


	private BanquierJpaRepository banquierJpa;
	
	@Autowired
	public BanquerServiceImpl( BanquierJpaRepository thebanquierJpa) {
		banquierJpa = thebanquierJpa;
	}


	@Override
	public List<banquier> findAll() {
		
		return banquierJpa.findAll();
	}

	@Override
	public Boolean existsById(Long id) {
		
		return banquierJpa.existsById(id);
	}



	@Override
	public
	Optional<banquier> findById(Long theId) {
		
	return banquierJpa.findById(theId);
	}


	@Override
	public List<banquier> findByAgencyId(Long agencyId) {
		
      List<banquier> result = banquierJpa.findByAgence_Id(agencyId);
		
		return result;
	}


	@Override
	public void addBanquier(banquier banquier) {
		banquierJpa.save(banquier);
		
	}


	@Override
	public banquier addbanquier(banquier thebanquier,Long id) {
		 thebanquier.setAgence(agenceService.findById(id));
		  Date date2 =new Date();
		     thebanquier.setJoiningDate(date2);
		     thebanquier.setDateNaissance(date2);
		   thebanquier.setJoiningDate(date2);
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
			
  		 clserv.sendemail(thebanquier.getEmail(),"votre nouveau mot de passe est:   "+ pass);

		   thebanquier.setPassword(passwordEncoder.encode(pass));
		 //  thebanquier.setPassword(passwordEncoder.encode(thebanquier.getPassword()));
          	banquerService.addBanquier(thebanquier);
			return thebanquier;		
	}



	@Override
	public banquier updateBanquier(banquier banquier) {
		
		return banquierJpa.saveAndFlush(banquier);
	}


	@Override
	public Optional<banquier> findByEmail(String email) {
		return banquierJpa.findByEmail(email);
	}


	@Override
	public boolean findCin(String ci) {
		List<String> cin =banquierJpa.findCin();
		for(String c :cin) {
			if(c.equals(ci))
				return false;
			
		}
		return true;
		
		
	}


	@Override
	public boolean findEmail(String em) {
		
		List<String> email =banquierJpa.findEmail();
		for(String c :email) {
			if(c.equals(em))
				return false;
			
		}
		return true;
		
	}

	

}






