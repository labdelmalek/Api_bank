package com.helfarre.BankApi.Services;

import java.util.List;
import java.util.Optional;

import com.helfarre.BankApi.Entities.admin;
import com.helfarre.BankApi.Entities.banquier;

public interface adminServ {

	List<admin> findAll();
	public void addadmin(admin adm);
	
	Optional<admin> findById(Long theId);
	
	Optional<admin> findByEmail(String email);
	
	public admin updateadmin(admin banquier);
	public Boolean existsById(Long id);
	
	public boolean findCin(String ci);
	public boolean findEmail(String em);

}
