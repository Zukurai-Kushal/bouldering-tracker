package com.fdm.bouldering_tracker.service;

import com.fdm.bouldering_tracker.model.Location;
import com.fdm.bouldering_tracker.model.Climb;
import com.fdm.bouldering_tracker.dto.LocationUpdateRequest;
import com.fdm.bouldering_tracker.exception.InvalidUserRequestException;
import com.fdm.bouldering_tracker.model.AppUser;
import com.fdm.bouldering_tracker.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final ClimbService climbService;

    @Autowired
    public LocationService(LocationRepository locationRepository, ClimbService climbService) {
        this.locationRepository = locationRepository;
        this.climbService = climbService;
    }

    public void deleteLocation(Location location) {
        // Nullify location references in climbs
        List<Climb> climbs = climbService.findByLocation(location);
        for (Climb climb : climbs) {
            climb.setLocation(null);
        }
        climbService.saveAll(climbs);

        // Delete the location
        locationRepository.delete(location);
    }

    public Location save(Location location) {
        return locationRepository.save(location);
    }

    public Optional<Location> findById(Long id) {
        return locationRepository.findById(id);
    }

    public Optional<Location> findByName(String name) {
        return locationRepository.findByName(name);
    }

    public List<Location> findAllByNameContaining(String namePart) {
        return locationRepository.findAllByNameContainingIgnoreCase(namePart);
    }

    public List<Location> findByCountry(String country) {
        return locationRepository.findByCountry(country);
    }

    public List<Location> findByRegion(String region) {
        return locationRepository.findByRegion(region);
    }

    public List<Location> findByCreator(AppUser creator) {
        return locationRepository.findByCreator(creator);
    }

    public List<Location> findByGpsBounds(double latStart, double latEnd, double longStart, double longEnd) {
        return locationRepository.findByGpsLatBetweenAndGpsLongBetween(latStart, latEnd, longStart, longEnd);
    }
    
    public List<Location> saveAll(List<Location> locations) {
    	return locationRepository.saveAll(locations);
    }
    
    public Location updateLocation(AppUser user, Long locationId, LocationUpdateRequest request) {
        Location location = locationRepository.findById(locationId)
            .orElseThrow(() -> new InvalidUserRequestException("Invalid location id: " + locationId));

        if (!location.getCreator().equals(user)) {
            throw new InvalidUserRequestException("You are not authorized to update this location.");
        }

        if (request.getName() != null) location.setName(request.getName());
        if (request.getType() != null) location.setType(request.getType());
        if (request.getCountry() != null) location.setCountry(request.getCountry());
        if (request.getRegion() != null) location.setRegion(request.getRegion());
        if (request.getGpsLat() != null) location.setGpsLat(request.getGpsLat());
        if (request.getGpsLong() != null) location.setGpsLong(request.getGpsLong());
        if (request.getLocationPhotoUrl() != null) location.setLocationPhotoUrl(request.getLocationPhotoUrl());

        return locationRepository.save(location);
    }
    
    public List<Location> searchByName(String namePart) {
        return locationRepository.findAllByNameContainingIgnoreCase(namePart);
    }
}