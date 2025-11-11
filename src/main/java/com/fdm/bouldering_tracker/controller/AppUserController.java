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
    @Operation(summary = "Get climbs for current user")
    public List<ClimbDTO> getUserClimbs(Principal principal) {
        AppUser user = appUserService.findByUsername(principal.getName())
                .orElseThrow(() -> new InvalidUserRequestException("User not found"));
        return climbService.findByUser(user).stream()
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
}
