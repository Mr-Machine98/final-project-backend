package com.mrmachine.apifinalapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mrmachine.apifinalapp.models.auth.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
	@Query("SELECT r FROM Role r WHERE r.name = :name")
	Optional<Role> findByName(@Param("name") String name);
}
