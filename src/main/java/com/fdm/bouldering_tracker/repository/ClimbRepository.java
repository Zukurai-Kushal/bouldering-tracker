package com.fdm.bouldering_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdm.bouldering_tracker.model.AppUser;
import com.fdm.bouldering_tracker.model.Climb;
import com.fdm.bouldering_tracker.model.FeatureTag;
import com.fdm.bouldering_tracker.model.Location;

public interface ClimbRepository extends JpaRepository<Climb, Long>{

    List<Climb> findByUser(AppUser user);
    List<Climb> findByLocation(Location location);
    List<Climb> findByFeaturesContaining(FeatureTag feature);
    List<Climb> findByFeaturesIn(List<FeatureTag> features);
    List<Climb> findBySharedTrue();
	
}
