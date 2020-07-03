package com.helfarre.BankApi.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.helfarre.BankApi.Entities.compte_epargne;

public interface epargneRepository extends JpaRepository<compte_epargne, Long> {

	Optional<List<compte_epargne>> findByClient_Id(Long id);
	Optional<List<compte_epargne>> findByLagence_Id(Long id);
	
	
	Optional<compte_epargne> findById(long id) ;
	
	List<compte_epargne>findAll();
	
	Optional<compte_epargne>   findByNumcompte(String numcompte) ;
	
	//	List<compte_epargne>findBy(Long id);
	/*@Transactional
    @Modifying
    @Query("UPDATE Report c SET c.name= :nameWHERE c.id = :id")
    void updateName(@Param("id") long id, @Param("name") String name);*/
/*	 @Query("select e from Employee e where e.dept = ?1")
	    void findByDepartment(String department);
	 }
*/
	Optional<compte_epargne> findByNuminternational(String numinternational);
	@Query(value="select numcompte from num ",nativeQuery=true)
	String getnumer();
	@Modifying(clearAutomatically = true)
	@Query(value="update num  n set  numcompte=n.numcompte+1",nativeQuery=true)
	void test();

}