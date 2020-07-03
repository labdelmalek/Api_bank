package com.helfarre.BankApi.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.helfarre.BankApi.Entities.typecarte;

public interface typecarteRepository extends JpaRepository<typecarte, Long> {
	typecarte   findByNom(String nom) ;


}
