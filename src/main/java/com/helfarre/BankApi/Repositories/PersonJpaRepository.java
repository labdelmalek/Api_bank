package com.helfarre.BankApi.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helfarre.BankApi.Entities.person;


public interface PersonJpaRepository extends JpaRepository<person, Long> {

	Optional<person> findByLastName(String lname);

	Optional<person> findByFirstName(String fname);
			
}
