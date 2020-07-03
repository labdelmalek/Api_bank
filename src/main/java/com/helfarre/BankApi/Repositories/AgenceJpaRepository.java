package com.helfarre.BankApi.Repositories;



import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.helfarre.BankApi.Entities.Agence;
public interface AgenceJpaRepository extends JpaRepository<Agence, Long> {
	
	Agence findByBanqs_Id(Long banquierId);
	
	
	@Query(value="select numagence from num ",nativeQuery=true)
	String getnumer();
	
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value="update num  n set  numagence=n.numagence+1",nativeQuery=true)
	void test();
	
	
	
	
	Optional<Agence> findByClis_Id(Long id);
	Optional<Agence> findByEmail(String email);
	Optional<Agence> findByPhone(String email);
	Optional<Agence> findByFax(String email);

	

}
