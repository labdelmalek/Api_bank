package com.helfarre.BankApi.Services;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helfarre.BankApi.Entities.Client;
import com.helfarre.BankApi.Entities.RequestCartCredit;

import com.helfarre.BankApi.Repositories.RequestCartCreditRepository;

import java.util.Calendar;
import org.springframework.scheduling.annotation.Scheduled;

import com.helfarre.BankApi.Entities.cbancaire;
import com.helfarre.BankApi.Entities.compte_cheque;
import com.helfarre.BankApi.Repositories.cbancaireRepository;


@Service
public class RequestCartCreditServiceImpl implements RequestCartCreditService {
	

	@Autowired
	private chequeService cheque;
	@Autowired
	private cbancaireRepository cbancaireRepositoryImpl;

	
	@Autowired
	private RequestCartCreditRepository requestcartcreditrepository;
	
	@Override
	public RequestCartCredit saveRequestCartCredit(RequestCartCredit requestCartCredit) {
		requestcartcreditrepository.save(requestCartCredit);
		return requestCartCredit;
	}

	@Override
	public RequestCartCredit updateRequestCartCredit(RequestCartCredit requestCartCredit) {
		requestcartcreditrepository.saveAndFlush(requestCartCredit);
		return requestCartCredit;
	}

	@Override
	public Optional<RequestCartCredit> deleteRequestCartCredit(Long idRequest) {
		Optional<RequestCartCredit> requestCartCredit = requestcartcreditrepository.findById(idRequest);
		requestcartcreditrepository.deleteById(idRequest);
		return requestCartCredit;
	}

	@Override
	public Optional<RequestCartCredit> getRequestCartCreditById(Long idRequest) {
		Optional<RequestCartCredit> requestCartCredit = requestcartcreditrepository.findById(idRequest);
		
		return requestCartCredit;
	}

	

	@Override
	public List<RequestCartCredit> findAll() {
		return requestcartcreditrepository.findAll();	
	}
	

	@Override
	public boolean verificationCloseRequest(RequestCartCredit req) {
	if(req.getCloseRequest() == true)
	{
		return true;
	}
	else return false;

}

	@Override
	public List<RequestCartCredit> getRequestCartCreditByCompteCheque(compte_cheque compteCheque){
		List<RequestCartCredit> requestCartCredit =requestcartcreditrepository.findByCheque(compteCheque);
		return requestCartCredit;
	}
	@Scheduled(cron = "0 0 4 */4 * ?")
    public void executer() {
        RequestCartCredit test =null;
        for (int i = 0; i < findAll().size(); i++) {
            test= findAll().get(i);
            String numcompte=null;
            if(test.getCloseRequest()==false) {
                cbancaire carte=new cbancaire();
                numcompte=test.getCheque().getNuminternational();
                Optional<compte_cheque> cl =cheque.getCompte_chequeByNuminternational(numcompte);
                compte_cheque compteCheque =cl.get();
                carte.setNom_carte(test.getCardType());
                carte.setStatus(true);
                String var = cbancaireRepositoryImpl.getnumer();
                while(var.length()<8)    {
                    var=0+var;
                    System.out.println(var);

                } 

                int x = (int)(Math.random()*((999)+1));
                System.out.println(x);
                String varib=""+x;
                while(varib.length()<3) {varib=0+varib;
                }
                carte.setCvv(varib);
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.YEAR, +5);
                carte.setExpiration(calendar.getTime());
                String agence=compteCheque.getLagence().getNumagence();
                carte.setNumcarte(agence+var);
                cbancaireRepositoryImpl.test();
                if(cheque.verificonditioncarte(compteCheque,carte) == true) {
                    carte.setCompte(compteCheque);
                    cbancaireRepositoryImpl.save(carte);

                }
                test.setCloseRequest(true);
                updateRequestCartCredit(test);
            }} 
        }
	@Override
    public List<RequestCartCredit> getRequestByClient(Client cl) {
        return requestcartcreditrepository.findByCheque_Client(cl);
    }

}
