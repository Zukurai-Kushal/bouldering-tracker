package com.fdm.bouldering_tracker.service;

import com.fdm.bouldering_tracker.dto.ClimbCreationRequest;
import com.fdm.bouldering_tracker.dto.ClimbUpdateRequest;
import com.fdm.bouldering_tracker.exception.InvalidUserRequestException;
import com.fdm.bouldering_tracker.exception.UnauthorizedClimbAccessException;
import com.fdm.bouldering_tracker.model.AppUser;
import com.fdm.bouldering_tracker.model.Climb;
import com.fdm.bouldering_tracker.model.FeatureTag;
import com.fdm.bouldering_tracker.model.Location;
import com.fdm.bouldering_tracker.repository.ClimbRepository;
import com.fdm.bouldering_tracker.repository.LocationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClimbService {
	
    private final ClimbRepository climbRepository;
    private final LocationRepository locationRepository;
    private final FeatureTagService featureTagService;

    @Autowired
    public ClimbService(
    		ClimbRepository climbRepository, 
    		LocationRepository locationRepository,
    		FeatureTagService featureTagService) {
        this.climbRepository = climbRepository;
        this.locationRepository = locationRepository;
        this.featureTagService = featureTagService;
    }

    public List<Climb> findByUser(AppUser user) {
        return climbRepository.findByUser(user);
    }

	public Climb createClimb(AppUser user, Location location, ClimbCreationRequest request, List<FeatureTag> features) {
	    Climb climb = new Climb(
	        user,
	        location,
	        request.getGrade(),
	        request.getScale() != null ? Climb.Scales.valueOf(request.getScale()) : null,
	        ZonedDateTime.now(),
	        request.getRating() != null ? request.getRating() : 0,
	        request.getShared() != null ? request.getShared() : true,
	        request.getAttempts() != null ? request.getAttempts() : 0,
	        features,
	        request.getComment(),
	        request.getBoulderName(),
	        request.getPhotoURL()
	    );
	    return climbRepository.save(climb);
	}

	public List<Climb> findByLocation(Location location) {
	    return climbRepository.findByLocation(location);
	}

	public List<Climb> findByFeatureTag(FeatureTag feature) {
	    return climbRepository.findByFeaturesContaining(feature);
	}

	public List<Climb> findByFeatureTags(List<FeatureTag> features) {
	    return climbRepository.findByFeaturesIn(features);
	}

	public Optional<Climb> findById(Long id) {
	    return climbRepository.findById(id);
	}

	public Climb save(Climb climb) {
	    return climbRepository.save(climb);
	}

	public void delete(Climb climb) {
	    climbRepository.delete(climb);
	}
	
	public List<Climb> findSharedClimbs() {
	    return climbRepository.findBySharedTrue();
	}
	
	public List<Climb> saveAll(List<Climb> climbs){
		return climbRepository.saveAll(climbs);
	}
	
	public Climb updateClimb(AppUser user, Long climbId, ClimbUpdateRequest request) {
	    Climb climb = climbRepository.findById(climbId)
	        .orElseThrow(() -> new InvalidUserRequestException("Invalid climb id: "+climbId));

	    if (!climb.getUser().equals(user)) {
	        throw new UnauthorizedClimbAccessException("You are not authorized to update this climb");
	    }

	    if (request.getGrade() != null) {
	        climb.setGrade(request.getGrade());
	    }

	    if (request.getScale() != null) {
	        try {
	            climb.setScale(Climb.Scales.valueOf(request.getScale()));
	        } catch (IllegalArgumentException e) {
	            throw new InvalidUserRequestException("Invalid scale value: " + request.getScale());
	        }
	    }

	    if (request.getRating() != null) {
	        climb.setRating(request.getRating());
	    }

	    if (request.getAttempts() != null) {
	        climb.setAttempts(request.getAttempts());
	    }

	    if (request.getComment() != null) {
	        climb.setComment(request.getComment());
	    }

	    if (request.getBoulderName() != null) {
	        climb.setBoulderName(request.getBoulderName());
	    }

	    if (request.getPhotoURL() != null) {
	        climb.setPhotoURL(request.getPhotoURL());
	    }

	    if (request.getShared() != null) {
	        climb.setShared(request.getShared());
	    }

	    if (request.getLocationId() != null) {
	        Location location = locationRepository.findById(request.getLocationId())
	            .orElseThrow(() -> new InvalidUserRequestException("Invalid location id: "+request.getLocationId()));
	        climb.setLocation(location);
	    }

	    if (request.getFeatureTagIds() != null && !request.getFeatureTagIds().isEmpty()) {
	        List<FeatureTag> features = featureTagService.findAllByIds(request.getFeatureTagIds());
	        climb.setFeatures(features);
	    }

	    return climbRepository.save(climb);
	}
	
	public void deleteClimb(AppUser user, Long climbId) {
	    Climb climb = climbRepository.findById(climbId)
	        .orElseThrow(() -> new InvalidUserRequestException("Invalid climb id: " + climbId));

	    if (!climb.getUser().equals(user)) {
	        throw new UnauthorizedClimbAccessException("You are not authorized to delete this climb");
	    }

	    climbRepository.delete(climb);
	}
}
