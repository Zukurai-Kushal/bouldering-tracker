package com.fdm.bouldering_tracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdm.bouldering_tracker.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long>{

	Optional<AppUser> findByUsername(String username);
	Optional<AppUser> findByEmail(String email);
	
}
