package com.helfarre.BankApi.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.helfarre.BankApi.Entities.Agence;
import com.helfarre.BankApi.Entities.compte;
import com.helfarre.BankApi.Entities.historiqueadmin;
import com.helfarre.BankApi.Repositories.BanquierJpaRepository;
import com.helfarre.BankApi.Repositories.chequeRepos;
import com.helfarre.BankApi.Repositories.compteRepository;
import com.helfarre.BankApi.Repositories.epargneRepository;
import com.helfarre.BankApi.Services.histoimpl;

@RestController
@RequestMapping("/histo")
public class historiqueController {
	@Autowired
	private histoimpl histserv;
	@Autowired
	private BanquierJpaRepository bgrepo;
	@Autowired
	private compteRepository compterepo;
	@Autowired
	private chequeRepos chequerepo;
	@Autowired
	private epargneRepository epargnerepo;

	@GetMapping("/{id}")
	
	public List<historiqueadmin>getbyagence(@PathVariable long id){

return histserv.getallbyagence(id);

		
		
	}
	
	@GetMapping("/infos/{id}")
	
	public List<Double>getinfo(@PathVariable long id){
List<Double>test=new ArrayList<Double>();
test.add( (double) bgrepo.findByAgence_Id(id).size());
Optional<List<compte>> ttt=compterepo.findByLagence_Id(id);
int suspendu=0,ouvert=0,cheque=0,epargne=0;
if(ttt.isPresent()) {
	

for(int i=0;i<ttt.get().size();i++) {
	if(ttt.get().get(i).getIs_suspended()==false) {
		ouvert++;
		if(ttt.get().get(i).getTypecompte().equals("cheque")) {cheque++;}
		else {if(ttt.get().get(i).getTypecompte().equals("epargne"))epargne++;}
	}
	else {
		suspendu++;
	}
	
}
}
test.add( (double) ouvert);
test.add( (double) suspendu);
test.add( (double) cheque);
test.add( (double) epargne);
		
	return test;	
	}

}
