package com.helfarre.BankApi.Repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.helfarre.BankApi.Entities.Transactionepaepa;


public interface TransactionRespository extends JpaRepository<Transactionepaepa, Long>{
	
	List<Transactionepaepa> findBySenderId(Long senderId) ;
	
	List<Transactionepaepa> findByReceiverId(Long receiverId) ;
	Optional <List<Transactionepaepa>> findByReceiverLagence(Long idagence);
	Optional <List<Transactionepaepa>> findBySenderLagence(Long idagence);
	List<Transactionepaepa> findBySenderIdAndType(Long id,String type);
	List<Transactionepaepa> findByReceiverIdAndType(Long id,String type);
	

}
