package com.helfarre.BankApi.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helfarre.BankApi.Entities.infocredit;
import com.helfarre.BankApi.Repositories.infocreditRepo;

@Service
public class infocreditImpl implements infocreditservice{
	@Autowired
	private infocreditRepo inforepo;

	@Override
	public infocredit findById(Long theId) {
		return inforepo.findByIdcredit(theId);
	}

	@Override
	public void addinfo(infocredit infoscredit) {
		inforepo.saveAndFlush(infoscredit);
		
	}

	@Override
	public void deleteById(Long theId) {
inforepo.deleteById(theId);		
	}

}
