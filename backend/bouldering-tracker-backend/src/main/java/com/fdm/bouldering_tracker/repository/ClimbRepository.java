package com.fdm.bouldering_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fdm.bouldering_tracker.model.AppUser;
import com.fdm.bouldering_tracker.model.Climb;
import com.fdm.bouldering_tracker.model.FeatureTag;
import com.fdm.bouldering_tracker.model.Location;

public interface ClimbRepository extends JpaRepository<Climb, Long>{

    List<Climb> findByUser(AppUser user);
    List<Climb> findByLocation(Location location);
    List<Climb> findByLocationOrderByDatetimeDesc(Location location);
    List<Climb> findByFeaturesContaining(FeatureTag feature);
    List<Climb> findByFeaturesIn(List<FeatureTag> features);
    List<Climb> findBySharedTrue();
    List<Climb> findByUserOrderByDatetimeDesc(AppUser user);
    List<Climb> findBySharedTrueOrderByDatetimeDesc();
    
    List<Climb> findByLocationLocationIdAndSharedTrueOrderByDatetimeDesc(Long locationId);
    
    @Query("SELECT c FROM Climb c WHERE c.shared = true " +
    	       "AND (:country IS NULL OR LOWER(c.location.country) = LOWER(:country)) " +
    	       "AND (:region IS NULL OR LOWER(c.location.region) = LOWER(:region)) " +
    	       "AND (:latStart IS NULL OR :latEnd IS NULL OR (c.location.gpsLat BETWEEN :latStart AND :latEnd)) " +
    	       "AND (:longStart IS NULL OR :longEnd IS NULL OR (c.location.gpsLong BETWEEN :longStart AND :longEnd)) " +
    	       "ORDER BY c.datetime DESC")
    	List<Climb> findSharedClimbsFiltered(@Param("country") String country,
    	                                     @Param("region") String region,
    	                                     @Param("latStart") Double latStart,
    	                                     @Param("latEnd") Double latEnd,
    	                                     @Param("longStart") Double longStart,
    	                                     @Param("longEnd") Double longEnd);
    
    @Query("SELECT c FROM Climb c WHERE c.user = :user " +
    	       "AND (:locationId IS NULL OR c.location.locationId = :locationId) " +
    	       "AND (:country IS NULL OR LOWER(c.location.country) = LOWER(:country)) " +
    	       "AND (:region IS NULL OR LOWER(c.location.region) = LOWER(:region)) " +
    	       "AND (:latStart IS NULL OR :latEnd IS NULL OR (c.location.gpsLat BETWEEN :latStart AND :latEnd)) " +
    	       "AND (:longStart IS NULL OR :longEnd IS NULL OR (c.location.gpsLong BETWEEN :longStart AND :longEnd)) " +
    	       "ORDER BY c.datetime DESC")
    	List<Climb> findUserClimbsFiltered(@Param("user") AppUser user,
    	                                   @Param("locationId") Long locationId,
    	                                   @Param("country") String country,
    	                                   @Param("region") String region,
    	                                   @Param("latStart") Double latStart,
    	                                   @Param("latEnd") Double latEnd,
    	                                   @Param("longStart") Double longStart,
    	                                   @Param("longEnd") Double longEnd);

}
