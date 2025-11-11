package com.fdm.bouldering_tracker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fdm.bouldering_tracker.dto.UpdateAppUserDTO;
import com.fdm.bouldering_tracker.exception.InvalidUserRequestException;
import com.fdm.bouldering_tracker.exception.UserAlreadyExistsException;
import com.fdm.bouldering_tracker.model.AppUser;
import com.fdm.bouldering_tracker.repository.AppUserRepository;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<AppUser> findByUserId(Long userId) {
        return appUserRepository.findById(userId);
    }
    
    public Optional<AppUser> findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    public Optional<AppUser> findByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    public boolean usernameExists(String username) {
        return appUserRepository.findByUsername(username).isPresent();
    }

    public boolean emailExists(String email) {
        return appUserRepository.findByEmail(email).isPresent();
    }

    public AppUser registerUser(String username, String email, String rawPassword) {
    	if (usernameExists(username) || emailExists(email)) {
    	    throw new UserAlreadyExistsException("Username or email already exists.");
    	}

        String hashedPassword = passwordEncoder.encode(rawPassword);
        AppUser newUser = new AppUser(username, email, hashedPassword);
        return appUserRepository.save(newUser);
    }
    
    public AppUser save(AppUser user) {
    	return appUserRepository.save(user);
    }
    
    public AppUser updateUser(AppUser user, UpdateAppUserDTO updateDTO) {
        if (updateDTO.getUsername() != null &&
            !user.getUsername().equals(updateDTO.getUsername()) &&
            usernameExists(updateDTO.getUsername())) {
            throw new UserAlreadyExistsException("Username already taken.");
        }

        if (updateDTO.getEmail() != null &&
            !user.getEmail().equals(updateDTO.getEmail()) &&
            emailExists(updateDTO.getEmail())) {
            throw new UserAlreadyExistsException("Email already taken.");
        }
        

		if (updateDTO.getEmail() != null && updateDTO.getEmail().isBlank()) {
		    throw new InvalidUserRequestException("Email cannot be blank.");
		}


        if (updateDTO.getUsername() != null) {
            user.setUsername(updateDTO.getUsername());
        }

        if (updateDTO.getEmail() != null ) {
            user.setEmail(updateDTO.getEmail());
        }

        if (updateDTO.getPassword() != null) {
            user.setPasswordHash(passwordEncoder.encode(updateDTO.getPassword()));;
        }

        return appUserRepository.save(user);
    }
    
    public void delete(AppUser user) {
    	appUserRepository.delete(user);
    }
    
    public List<AppUser> findAllUsers(){
    	return appUserRepository.findAll();
    }
}
