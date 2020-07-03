package com.helfarre.BankApi.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helfarre.BankApi.Entities.historiqueadmin;
import com.helfarre.BankApi.Entities.infocredit;

public interface infocreditRepo extends JpaRepository<infocredit, Long> {
	List<infocredit> findByIdagence(Long idagence);
	infocredit findByIdcredit(Long idcredit);

}
