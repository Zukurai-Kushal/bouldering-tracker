package com.fdm.bouldering_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdm.bouldering_tracker.model.Climb;

public interface ClimbRepository extends JpaRepository<Climb, Long>{

}
