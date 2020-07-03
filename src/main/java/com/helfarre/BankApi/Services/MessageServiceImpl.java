/*package com.helfarre.BankApi.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helfarre.BankApi.Entities.message;
import com.helfarre.BankApi.Repositories.MessageJpaRepository;



@Service
public class MessageServiceImpl implements MessageService {

	private MessageJpaRepository MessageJpaRepositoryImpl;
	
	@Autowired
	public MessageServiceImpl(MessageJpaRepository theMessageJpaRepositoryImpl) {
		MessageJpaRepositoryImpl = theMessageJpaRepositoryImpl;
	}
	
	@Override
	public List<message> findAll() {
		return MessageJpaRepositoryImpl.findAll();
	}
	
	
	@Override
	public message findById(Long theId) {
		Optional<message> result = MessageJpaRepositoryImpl.findById(theId);
		
		message theAgence = null;
		
		if (result.isPresent()) {
			theAgence = result.get();
		}
		else {
			
			throw new RuntimeException("Did not find  message id - " + theId);
		}
		
		return theAgence;
	}
	

	@Override
	public void addMessage(message messag) {
		MessageJpaRepositoryImpl.save( messag);
		
	}

	@Override
	public void deleteMessage(Long id) {
		MessageJpaRepositoryImpl.deleteById(id);
		
	}

	@Override
	public Boolean existsById(Long id) {
		
		return MessageJpaRepositoryImpl.existsById(id);
	}

	@Override
	public List<message> findByClients(Long id) {
		
		return MessageJpaRepositoryImpl.findByLesClients_Id(id);
	}

	@Override
	public List<message> findByAgence(Long id) {
		
		return MessageJpaRepositoryImpl.findByContacts_Id(id);
	}

	



	

}

*/




