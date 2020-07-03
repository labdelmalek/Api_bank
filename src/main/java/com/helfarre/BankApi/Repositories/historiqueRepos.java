package com.helfarre.BankApi.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helfarre.BankApi.Entities.historiqueadmin;

public interface historiqueRepos  extends JpaRepository<historiqueadmin, Long>{
	List<historiqueadmin> findByIdagence(Long idagence);
	

}
