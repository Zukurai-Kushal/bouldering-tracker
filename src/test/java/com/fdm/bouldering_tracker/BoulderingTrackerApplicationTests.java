package com.fdm.bouldering_tracker;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.fdm.bouldering_tracker.model.AppUser;
import com.fdm.bouldering_tracker.repository.AppUserRepository;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
class BoulderingTrackerApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
	private AppUserRepository appUserRepository;

	@Test
	void testSaveAndFindUser() {
	    AppUser user = new AppUser();
	    user.setUsername("testuser");
	    user.setEmail("test@example.com");
	    user.setPasswordHash("hashedpassword");

	    appUserRepository.save(user);

	    Optional<AppUser> found = appUserRepository.findByUsername("testuser");
	    assertTrue(found.isPresent());
	}

}

