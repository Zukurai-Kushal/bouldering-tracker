package com.fdm.bouldering_tracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fdm.bouldering_tracker.model.AppUser;
import com.fdm.bouldering_tracker.repository.AppUserRepository;

@SpringBootApplication
public class BoulderingTrackerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BoulderingTrackerApplication.class, args);
	}

}