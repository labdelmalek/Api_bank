package com.helfarre.BankApi.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.helfarre.BankApi.Entities.Client;
import com.helfarre.BankApi.Entities.RequestCC;

import com.helfarre.BankApi.Entities.ccheque;
import com.helfarre.BankApi.Entities.compte_cheque;
import com.helfarre.BankApi.Repositories.RequestCCRepository;

@Service
public class RequestCCServiceImpl implements RequestCCService {
	
	@Autowired
	private RequestCCRepository requestCCrepository;
	@Autowired
	private  chequeService serv;
	@Autowired
	private  cchequeService servii;
	@Override
	public RequestCC saveRequestCC(RequestCC requestCC) {
		requestCCrepository.save(requestCC);
		return requestCC;
	}

	@Override
	public RequestCC updateRequestCC(RequestCC requestCC) {
		requestCCrepository.saveAndFlush(requestCC);
		return requestCC;
	}

	@Override
	public Optional<RequestCC> deleteRequestCC(Long idRequest) {
		Optional<RequestCC> requestCC = requestCCrepository.findById(idRequest);
		requestCCrepository.deleteById(idRequest);
		return (requestCC);
	}

	@Override
	public Optional<RequestCC> getRequestCCById(Long idRequest) {
		Optional<RequestCC> requestCC = requestCCrepository.findById(idRequest);
		return requestCC;
	}

	
	@Override
	public List<RequestCC> findAll() {
		
		return requestCCrepository.findAll();
	}
	
	@Override
	public List<RequestCC> getRequestCCByCompteCheque(compte_cheque compteCheque){

		List<RequestCC> requestCC = requestCCrepository.findByCheque(compteCheque);
		return requestCC;
	}
	@Scheduled(cron = " 0 0 4 */2 * ?")

	  public void executer() {
		RequestCC test =null;
		
		for (int i = 0; i < findAll().size(); i++) {
		
			
			
		  test= findAll().get(i);
		 
		  if(test.getCloseRequest()==false) {
			  ccheque cheque=new ccheque();
			  
			  
				Optional<compte_cheque> cl =serv.getCompte_chequeByNuminternational(test.getCheque().getNuminternational());
				
					if(serv.verifconditioncarte(cl.get())==true) {
					cheque.setCompte(cl.get());
					cheque.setFrais_ch(30);
					cheque.setNb_pages(50);
					cheque.setRib(test.getCheque().getRib());
					 servii.saveOrUpdateCcheque(cheque);
			
	 
		test.setCloseRequest(true);  
		updateRequestCC(test);
		  
		}}}


}

	@Override
	public List<RequestCC> gerRequestByClient(Client cl) {
		return requestCCrepository.findByCheque_Client(cl);
	}}