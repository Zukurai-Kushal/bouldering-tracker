package com.fdm.bouldering_tracker.service;

import com.fdm.bouldering_tracker.model.AppUser;
import com.fdm.bouldering_tracker.model.Climb;
import com.fdm.bouldering_tracker.repository.ClimbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClimbService {
	
    private final ClimbRepository climbRepository;

    @Autowired
    public ClimbService(ClimbRepository climbRepository) {
        this.climbRepository = climbRepository;
    }

    public List<Climb> findByUser(AppUser user) {
        return climbRepository.findByUser(user);
    }
    
}
