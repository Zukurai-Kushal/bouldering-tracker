package com.fdm.bouldering_tracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdm.bouldering_tracker.model.AppUser;
import com.fdm.bouldering_tracker.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long>{

    Optional<Location> findByName(String name);

    List<Location> findAllByNameContainingIgnoreCase(String namePart);

    List<Location> findByCountry(String country);

    List<Location> findByRegion(String region);

    List<Location> findByCreator(AppUser creator);
    
    List<Location> findByGpsLatBetweenAndGpsLongBetween(double latStart, double latEnd, double longStart, double longEnd);
    
}
