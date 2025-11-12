package com.fdm.bouldering_tracker.controller;

import com.fdm.bouldering_tracker.dto.*;
import com.fdm.bouldering_tracker.exception.InvalidUserRequestException;
import com.fdm.bouldering_tracker.model.*;
import com.fdm.bouldering_tracker.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class AppUserController {

    private final AppUserService appUserService;
    private final ClimbService climbService;
    private final LocationService locationService;
    private final FeatureTagService featureTagService;

    @Autowired
    public AppUserController(AppUserService appUserService,
                             ClimbService climbService,
                             LocationService locationService,
                             FeatureTagService featureTagService) {
        this.appUserService = appUserService;
        this.climbService = climbService;
        this.locationService = locationService;
        this.featureTagService = featureTagService;
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user info")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User info retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public AppUserDTO getCurrentUser(Principal principal) {
        AppUser user = appUserService.findByUsername(principal.getName())
                .orElseThrow(() -> new InvalidUserRequestException("User not found"));
        return new AppUserDTO(user);
    }

    @PutMapping("/me")
    @Operation(summary = "Update current user info")
    public AppUserDTO updateCurrentUser(Principal principal,
                                        @Valid @RequestBody AppUserUpdateRequest updateDTO) {
        AppUser user = appUserService.findByUsername(principal.getName())
                .orElseThrow(() -> new InvalidUserRequestException("User not found"));
        AppUser updatedUser = appUserService.updateUser(user, updateDTO);
        return new AppUserDTO(updatedUser);
    }

    @DeleteMapping("/me")
    @Operation(summary = "Delete current user")
    public void deleteCurrentUser(Principal principal) {
        AppUser user = appUserService.findByUsername(principal.getName())
                .orElseThrow(() -> new InvalidUserRequestException("User not found"));
        appUserService.deleteUser(user);
    }
    
    @GetMapping("/me/climbs")
    @Operation(summary = "Get climbs for current user, sorted by newest first")
    public List<ClimbDTO> getUserClimbs(Principal principal) {
        AppUser user = appUserService.findByUsername(principal.getName())
            .orElseThrow(() -> new InvalidUserRequestException("User not found"));

        return climbService.findByUserSorted(user).stream()
            .map(ClimbDTO::new)
            .collect(Collectors.toList());
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public AppUserDTO register(@Valid @RequestBody RegistrationRequest request) {
        AppUser newUser = appUserService.registerUser(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );
        return new AppUserDTO(newUser);
    }

    @PostMapping("/me/climbs")
    @Operation(summary = "Create climb for current user")
    public ClimbDTO createClimb(Principal principal,
                                @Valid @RequestBody ClimbCreationRequest request) {
        AppUser user = appUserService.findByUsername(principal.getName())
                .orElseThrow(() -> new InvalidUserRequestException("User not found"));

        Location location = request.getLocationId() != null
                ? locationService.findById(request.getLocationId()).orElse(null)
                : null;

        List<FeatureTag> features = request.getFeatureTagIds() != null && !request.getFeatureTagIds().isEmpty()
                ? featureTagService.findAllByIds(request.getFeatureTagIds())
                : List.of();

        Climb climb = climbService.createClimb(user, location, request, features);
        return new ClimbDTO(climb);
    }

    @PutMapping("/me/climbs/{climbId}")
    @Operation(summary = "Update a climb for the current user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Climb updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "403", description = "User not authorized to update this climb"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ClimbDTO updateClimb(Principal principal,
                                @PathVariable Long climbId,
                                @Valid @RequestBody ClimbUpdateRequest request) {
        AppUser user = appUserService.findByUsername(principal.getName())
                .orElseThrow(() -> new InvalidUserRequestException("User not found"));
        Climb updatedClimb = climbService.updateClimb(user, climbId, request);
        return new ClimbDTO(updatedClimb);
    }
    
    @DeleteMapping("/me/climbs/{climbId}")
    @Operation(summary = "Delete a climb for the current user")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Climb deleted successfully"),
        @ApiResponse(responseCode = "403", description = "User not authorized to delete this climb"),
        @ApiResponse(responseCode = "404", description = "Climb not found")
    })
    public void deleteClimb(Principal principal, @PathVariable Long climbId) {
        AppUser user = appUserService.findByUsername(principal.getName())
            .orElseThrow(() -> new InvalidUserRequestException("User not found"));

        climbService.deleteClimb(user, climbId);
    }
    
    @PostMapping("/me/locations")
    @Operation(summary = "Create a location for the current user")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Location created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public LocationDTO createLocation(Principal principal,
                                      @Valid @RequestBody LocationCreationRequest request) {
        AppUser user = appUserService.findByUsername(principal.getName())
            .orElseThrow(() -> new InvalidUserRequestException("User not found"));

        Location location = new Location();
        location.setCreator(user);
        location.setName(request.getName());
        location.setType(request.getType());
        location.setCountry(request.getCountry());
        location.setRegion(request.getRegion());
        if (request.getGpsLat() != null) location.setGpsLat(request.getGpsLat());
        if (request.getGpsLong() != null) location.setGpsLong(request.getGpsLong());
        location.setLocationPhotoUrl(request.getLocationPhotoUrl());

        Location savedLocation = locationService.save(location);
        return new LocationDTO(savedLocation);
    }
    
    @PutMapping("/me/locations/{locationId}")
    @Operation(summary = "Update a location for the current user")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Location updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "403", description = "User not authorized to update this location"),
        @ApiResponse(responseCode = "404", description = "Location not found")
    })
    public LocationDTO updateLocation(Principal principal,
                                      @PathVariable Long locationId,
                                      @Valid @RequestBody LocationUpdateRequest request) {
        AppUser user = appUserService.findByUsername(principal.getName())
            .orElseThrow(() -> new InvalidUserRequestException("User not found"));

        Location updatedLocation = locationService.updateLocation(user, locationId, request);
        return new LocationDTO(updatedLocation);
    }
    
    @GetMapping("/me/locations")
    @Operation(summary = "Get all locations created by the current user")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Locations retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public List<LocationDTO> getUserLocations(Principal principal) {
        AppUser user = appUserService.findByUsername(principal.getName())
            .orElseThrow(() -> new InvalidUserRequestException("User not found"));

        return locationService.findByCreator(user).stream()
            .map(LocationDTO::new)
            .collect(Collectors.toList());
    }
    
    @DeleteMapping("/me/locations/{locationId}")
    @Operation(summary = "Delete a location created by the current user")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Location deleted successfully"),
        @ApiResponse(responseCode = "403", description = "User not authorized to delete this location"),
        @ApiResponse(responseCode = "404", description = "Location not found")
    })
    public ResponseEntity<Void> deleteLocation(Principal principal,
                                               @PathVariable Long locationId) {
        AppUser user = appUserService.findByUsername(principal.getName())
            .orElseThrow(() -> new InvalidUserRequestException("User not found"));

        Location location = locationService.findById(locationId)
            .orElseThrow(() -> new InvalidUserRequestException("Location not found"));

        if (!location.getCreator().equals(user)) {
            throw new InvalidUserRequestException("You are not authorized to delete this location.");
        }

        locationService.deleteLocation(location);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/me/feature-tags")
    @Operation(summary = "Create a feature tag for the current user")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Feature tag created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input or duplicate name"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public FeatureTagDTO createFeatureTag(Principal principal,
                                          @Valid @RequestBody FeatureTagCreationRequest request) {
        AppUser user = appUserService.findByUsername(principal.getName())
            .orElseThrow(() -> new InvalidUserRequestException("User not found"));

        featureTagService.findByName(request.getName()).ifPresent(tag -> {
            throw new InvalidUserRequestException("Feature tag with this name already exists");
        });

        FeatureTag tag = new FeatureTag(request.getName());
        FeatureTag savedTag = featureTagService.save(tag);

        return new FeatureTagDTO(savedTag);
    }
    
    @GetMapping("/me/climbs/search")
    @Operation(summary = "Search all climbs of the current user by location, country, region, or GPS range")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Filtered climbs retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public List<ClimbDTO> searchUserClimbs(
            Principal principal,
            @RequestParam(required = false) Long locationId,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) Double latStart,
            @RequestParam(required = false) Double latEnd,
            @RequestParam(required = false) Double longStart,
            @RequestParam(required = false) Double longEnd) {

        AppUser user = appUserService.findByUsername(principal.getName())
                .orElseThrow(() -> new InvalidUserRequestException("User not found"));

        return climbService.findUserClimbsFiltered(user, locationId, country, region, latStart, latEnd, longStart, longEnd)
                .stream()
                .map(ClimbDTO::new)
                .collect(Collectors.toList());
    }
}
