package com.mrmachine.apifinalapp.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mrmachine.apifinalapp.models.auth.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	
	@Query("SELECT u from User u WHERE u.username = :username")
	Optional<User> findByUsername(@Param("username") String username);
	
	Page<User> findAll(Pageable pageable);
}
