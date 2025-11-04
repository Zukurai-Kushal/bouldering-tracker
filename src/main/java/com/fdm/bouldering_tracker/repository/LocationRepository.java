package com.fdm.bouldering_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdm.bouldering_tracker.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long>{

}
