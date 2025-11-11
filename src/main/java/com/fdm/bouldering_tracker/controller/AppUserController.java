package com.fdm.bouldering_tracker.controller;

import com.fdm.bouldering_tracker.BoulderingTrackerApplication;
import com.fdm.bouldering_tracker.dto.AppUserDTO;
import com.fdm.bouldering_tracker.dto.ClimbDTO;
import com.fdm.bouldering_tracker.dto.RegistrationRequest;
import com.fdm.bouldering_tracker.dto.UpdateAppUserDTO;
import com.fdm.bouldering_tracker.model.AppUser;
import com.fdm.bouldering_tracker.model.Climb;
import com.fdm.bouldering_tracker.service.AppUserService;
import com.fdm.bouldering_tracker.service.ClimbService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class AppUserController {

    private final AppUserService appUserService;
    private final ClimbService climbService;

    @Autowired
    public AppUserController(AppUserService appUserService, ClimbService climbService) {
        this.appUserService = appUserService;
        this.climbService = climbService;
    }

    @Operation(summary = "Get current user info")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User info retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/me")
    public ResponseEntity<AppUserDTO> getCurrentUser(Principal principal) {
        Optional<AppUser> userOpt = appUserService.findByUsername(principal.getName());
        return userOpt.map(user -> ResponseEntity.ok(new AppUserDTO(user)))
                      .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update current user info")
    @PutMapping("/me")
    public ResponseEntity<AppUserDTO> updateCurrentUser(
        Principal principal,
        @Valid @RequestBody UpdateAppUserDTO updateDTO
    ) {
        Optional<AppUser> userOpt = appUserService.findByUsername(principal.getName());
        if (userOpt.isEmpty()) return ResponseEntity.notFound().build();

        AppUser updatedUser = appUserService.updateUser(userOpt.get(), updateDTO);
        return ResponseEntity.ok(new AppUserDTO(updatedUser));
    }

    @Operation(summary = "Delete current user")
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteCurrentUser(Principal principal) {
        Optional<AppUser> userOpt = appUserService.findByUsername(principal.getName());
        if (userOpt.isEmpty()) return ResponseEntity.notFound().build();

        appUserService.delete(userOpt.get());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get climbs for current user")
    @GetMapping("/me/climbs")
    public ResponseEntity<List<ClimbDTO>> getUserClimbs(Principal principal) {
        Optional<AppUser> userOpt = appUserService.findByUsername(principal.getName());
        if (userOpt.isEmpty()) return ResponseEntity.notFound().build();

        List<Climb> climbs = climbService.findByUser(userOpt.get());
        List<ClimbDTO> climbDTOs = climbs.stream().map(ClimbDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(climbDTOs);
    }
    
    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<AppUserDTO> register(@Valid @RequestBody RegistrationRequest request) {
        AppUser newUser = appUserService.registerUser(
            request.getUsername(),
            request.getEmail(),
            request.getPassword()
        );
        return ResponseEntity.ok(new AppUserDTO(newUser));
    }
} 
