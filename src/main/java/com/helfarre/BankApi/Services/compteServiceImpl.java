package com.helfarre.BankApi.Services;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helfarre.BankApi.Entities.compte;
import com.helfarre.BankApi.Entities.compte_epargne;
import com.helfarre.BankApi.Repositories.compteRepository;
import com.helfarre.BankApi.Repositories.epargneRepository;


@Service
public class compteServiceImpl implements compteService{
	@Autowired
	private compteRepository compteRepo;
	@Autowired
	private epargneService epargneServi;
	@Autowired
	private epargneRepository epargneRepo;
	@Autowired
	private ClientService clserv;
	@Override
	public Optional<compte> getCompteById(Long id) {
		Optional<compte> compte=compteRepo.findById(id);
		return compte;
	}


	@Override
	public compte saveOrUpdateCompte(compte compte) {
		compteRepo.save(compte);		
			return compte;
	}

//	@Override
//	public Optional<compte> deleteAccount(Long id) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
	@Override
	public List<compte> findAll() {
		
		return compteRepo.findAll();
	}


	@Override
	public Optional<List<compte>> getCompteByClient(Long id) {
		return compteRepo.findByClient_Id(id);
	}
	@Override
	public void datedernier(long id) {
		Optional<compte_epargne> cheque=epargneRepo.findById(id);
		if (cheque.isPresent()) {
		Date date2 =new Date();
		
		cheque.get().setDernier_interet(date2);
		epargneServi.saveOrUpdateCompte_epargne(cheque.get());		
		}	
	}

	@Override
	public boolean retraitcompte(long id, float somme) {
		Optional<compte> cheque=compteRepo.findById(id);
		compte test=cheque.get();
		
		if(test.getIs_suspended() == false && (float)test.getBalance()>=(float)somme) {

		test.setBalance((float)test.getBalance()-(float)somme);
		saveOrUpdateCompte(test);
		if(test.getTypecompte()=="epargne") {
			datedernier(id);
		}
		clserv.sendemail(test.getClient().getEmail(), "On confirme le retrait, de votre compte dont le "+"N° international est: "+test.getNuminternational()+",de la somme "+somme+"\n solde actuel:"+test.getBalance());

		return true;
		}
		return false;
	}
	
	

	
	@Override
	public boolean creditcompte(long id, float somme) {
		Optional<compte> cheque=compteRepo.findById(id);
		compte test=cheque.get();
		if(test.getIs_suspended()==false) {
		test.setBalance(test.getBalance()+somme-1);
		saveOrUpdateCompte(test);
		clserv.sendemail(test.getClient().getEmail(), "On confirme que votre compte  dont le N° international est: "+test.getNuminternational()+"a été credité de la somme "+(somme-1)+"\n solde actuel:"+test.getBalance());

		return true;
		}return false;
	}


	@Override
	public Optional<List<compte>> getCompteByAgenceId(Long id) {
		
		return compteRepo.findByLagence_Id(id);
	}

	@Override
	public Optional<compte> getCompteByNum(String accountnum) {
		
		return compteRepo.findByNumcompte(accountnum);
	}

	@Override
	public Optional<compte> getCompteByNuminternational(String accountnum) {
		
		return compteRepo.findByNuminternational(accountnum);
	}
}
