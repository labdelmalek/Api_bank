package com.helfarre.BankApi.Services;

import java.util.List;
import java.util.Optional;

import com.helfarre.BankApi.Entities.banquier;

public interface BanquerService {

	List<banquier> findAll();
	public void addBanquier(banquier banquier);
	
	Optional<banquier> findById(Long theId);
	
	Optional<banquier> findByEmail(String email);
	
	List<banquier> findByAgencyId(Long agencyId);
	
	

	public banquier updateBanquier(banquier banquier);
	public Boolean existsById(Long id);
	
	public boolean findCin(String ci);
	public boolean findEmail(String em);
	banquier addbanquier(banquier banquier,Long id);

}
