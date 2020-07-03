package com.helfarre.BankApi.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helfarre.BankApi.Entities.ccheque;
import com.helfarre.BankApi.Entities.compte_cheque;
import com.helfarre.BankApi.Repositories.cchequeRepository;
@Service
public class cchequeServiceImpl implements cchequeService{
	@Autowired
	private cchequeRepository cchequeRepo;
	@Override
	public List<ccheque> getAllCcheque() {
		// TODO Auto-generated method stub
		return cchequeRepo.findAll();
	}

	@Override
	public Optional<ccheque> getCchequeById(Long id) {
Optional<ccheque>carnet=cchequeRepo.findById(id);
		return carnet;
	}

	@Override
	public ccheque saveOrUpdateCcheque(ccheque cheque) {
cchequeRepo.save(cheque);	
return cheque;
	}



	@Override
	public Optional<List<ccheque>> getCchequeByCompte(compte_cheque compte) {
Optional<List<ccheque>>cheque=cchequeRepo.findByCompte(compte);
		return cheque;
	}
	

}
