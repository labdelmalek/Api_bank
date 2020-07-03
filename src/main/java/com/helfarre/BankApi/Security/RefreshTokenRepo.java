package com.helfarre.BankApi.Security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {
	
	Optional<RefreshToken> findByUserId(String string);

	
	Optional<RefreshToken> findByToken(String token);

}
