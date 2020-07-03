package com.helfarre.BankApi.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helfarre.BankApi.Entities.admin;
import com.helfarre.BankApi.Entities.banquier;
import com.helfarre.BankApi.Repositories.BanquierJpaRepository;
import com.helfarre.BankApi.Repositories.adminRepo;

@Service
public class adminservimpl implements adminServ {

@Autowired

	private adminRepo adminJpa;
	



	@Override
	public List<admin> findAll() {
		List<admin> testyt= adminJpa.findAll();
		
		return testyt;
	}

	@Override
	public Boolean existsById(Long id) {
		
		return adminJpa.existsById(id);
	}



	@Override
	public
	Optional<admin> findById(Long theId) {
		
	return adminJpa.findById(theId);
	}


	@Override
	public void addadmin(admin banquier) {
		adminJpa.save(banquier);
		
	}





	@Override
	public admin updateadmin(admin banquier) {
		
		return adminJpa.saveAndFlush(banquier);
	}


	@Override
	public Optional<admin> findByEmail(String email) {
		return adminJpa.findByEmail(email);
	}


	@Override
	public boolean findCin(String ci) {
		List<String> cin =adminJpa.findCin();
		for(String c :cin) {
			if(c.equals(ci))
				return false;
			
		}
		return true;
		
		
	}


	@Override
	public boolean findEmail(String em) {
		
		List<String> email =adminJpa.findEmail();
		for(String c :email) {
			if(c.equals(em))
				return false;
			
		}
		return true;
		
	}


	
}
