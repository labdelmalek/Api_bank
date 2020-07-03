package com.helfarre.BankApi.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helfarre.BankApi.Entities.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {

}
