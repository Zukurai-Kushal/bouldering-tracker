package com.fdm.bouldering_tracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fdm.bouldering_tracker.model.User;
import com.fdm.bouldering_tracker.repository.UserRepository;


@SpringBootApplication
public class BoulderingTrackerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BoulderingTrackerApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner userDummyDataLoader(UserRepository repository) {
//		return (args) -> {
//	      repository.save(new User("Alex", "alex@gmail.com", "2bb12bb768eb669f0e4b9df29e22a00467eb513c275ccfff1013288facac7889"));
//	      repository.save(new User("Jamie", "jamie@example.com", "a1f5c3d2e8b9f0a4c7d6e2b3a9f8e7d6c5b4a3f2e1d0c9b8a7f6e5d4c3b2a1f0"));
//	      repository.save(new User("Morgan", "morgan@example.com", "3c4d5e6f7a8b9c0d1e2f3a4b5c6d7e8f9a0b1c2d3e4f5a6b7c8d9e0f1a2b3c4"));
//	      repository.save(new User("Taylor", "taylor@example.com", "9f8e7d6c5b4a3f2e1d0c9b8a7f6e5d4c3b2a1f0a1b2c3d4e5f6a7b8c9d0e1f2"));
//	      repository.save(new User("Jordan", "jordan@example.com", "e1f2d3c4b5a6f7e8d9c0b1a2f3e4d5c6b7a8f9e0d1c2b3a4f5e6d7c8b9a0f1"));
//	      };
//	}

}