package com.fdm.bouldering_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdm.bouldering_tracker.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
