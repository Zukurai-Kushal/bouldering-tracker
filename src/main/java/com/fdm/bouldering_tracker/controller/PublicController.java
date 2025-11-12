package com.fdm.bouldering_tracker.controller;

import com.fdm.bouldering_tracker.dto.ClimbDTO;
import com.fdm.bouldering_tracker.dto.FeatureTagDTO;
import com.fdm.bouldering_tracker.dto.LocationDTO;
import com.fdm.bouldering_tracker.model.Climb;
import com.fdm.bouldering_tracker.model.FeatureTag;
import com.fdm.bouldering_tracker.service.ClimbService;
import com.fdm.bouldering_tracker.service.FeatureTagService;
import com.fdm.bouldering_tracker.service.LocationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/public")
public class PublicController {

    private final ClimbService climbService;
    private final FeatureTagService featureTagService;
    private final LocationService locationService;    

    @Autowired
    public PublicController(ClimbService climbService, FeatureTagService featureTagService, LocationService locationService) {
        this.climbService = climbService;
        this.featureTagService = featureTagService;
        this.locationService = locationService;
    }

    // Get all shared climbs sorted by newest first
    @GetMapping("/climbs")
    @Operation(summary = "Get all shared climbs sorted by newest first")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Shared climbs retrieved successfully")
    })
    public List<ClimbDTO> getSharedClimbs() {
        return climbService.findSharedClimbsSorted().stream()
                .map(ClimbDTO::new)
                .collect(Collectors.toList());
    }
    
    // Get all shared climbs sorted by newest first + search filters
    @GetMapping("/climbs/search")
    @Operation(summary = "Search shared climbs by location, country, region, or GPS range")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Filtered climbs retrieved successfully")
    })
    public List<ClimbDTO> searchSharedClimbs(
            @RequestParam(required = false) Long locationId,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) Double latStart,
            @RequestParam(required = false) Double latEnd,
            @RequestParam(required = false) Double longStart,
            @RequestParam(required = false) Double longEnd) {

        return climbService.findSharedClimbsFiltered(locationId, country, region, latStart, latEnd, longStart, longEnd)
                .stream()
                .map(ClimbDTO::new)
                .collect(Collectors.toList());
    }

    // Get all feature tags or search by name
    @GetMapping("/feature-tags")
    @Operation(summary = "Get all feature tags or search by name")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Feature tags retrieved successfully")
    })
    public List<FeatureTagDTO> getFeatureTags(@RequestParam(required = false) String name) {
        List<FeatureTag> tags = (name != null && !name.isBlank())
                ? featureTagService.searchByName(name)
                : featureTagService.findAllSorted();

        return tags.stream().map(FeatureTagDTO::new).collect(Collectors.toList());
    }
    
    @GetMapping("/locations/search")
    @Operation(summary = "Search locations by name (case-insensitive, partial match)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Locations retrieved successfully")
    })
    public List<LocationDTO> searchLocations(@RequestParam String name) {
        return locationService .searchByName(name).stream()
                .map(LocationDTO::new)
                .collect(Collectors.toList());
    }
    
}