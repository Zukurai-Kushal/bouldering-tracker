package com.fdm.bouldering_tracker.config;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fdm.bouldering_tracker.model.Climb;
import com.fdm.bouldering_tracker.model.FeatureTag;
import com.fdm.bouldering_tracker.model.Location;
import com.fdm.bouldering_tracker.model.User;
import com.fdm.bouldering_tracker.repository.ClimbRepository;
import com.fdm.bouldering_tracker.repository.FeatureTagRepository;
import com.fdm.bouldering_tracker.repository.LocationRepository;
import com.fdm.bouldering_tracker.repository.UserRepository;

@Configuration
public class DataLoader {

	@Bean
	public CommandLineRunner dummyDataLoader(UserRepository userRepository, LocationRepository locationRepository, ClimbRepository climbRepository, FeatureTagRepository featureTagRepository) {
		return (args) -> {
			
			// Create Users
			User user1 = new User("Alex", "alex@gmail.com", "2bb12bb768eb669f0e4b9df29e22a00467eb513c275ccfff1013288facac7889");
			User user2 = new User("Jamie", "jamie@example.com", "a1f5c3d2e8b9f0a4c7d6e2b3a9f8e7d6c5b4a3f2e1d0c9b8a7f6e5d4c3b2a1f0");
			User user3 = new User("Riley", "riley@example.com", "f0e1d2c3b4a5f6e7d8c9b0a1f2e3d4c5b6a7f8e9d0c1b2a3f4e5d6c7b8a9f0");
			User user4 = new User("Casey", "casey@example.com", "b1c2d3e4f5a6b7c8d9e0f1a2b3c4d5e6f7a8b9c0d1e2f3a4b5c6d7e8f9a0b1");
			User user5 = new User("Drew", "drew@example.com", "c3d4e5f6a7b8c9d0e1f2a3b4c5d6e7f8a9b0c1d2e3f4a5b6c7d8e9f0a1b2c3");

			userRepository.save(user1);
			userRepository.save(user2);
			userRepository.save(user3);
			userRepository.save(user4);
			userRepository.save(user5);

			// Create Locations
			Location location1 = new Location(user2, "Sunset Forest", Location.Types.USER_GENERATED);
			location1.setCountry("Hong Kong SAR");
			location1.setRegion("Ngau Chi Wan");
			location1.setGps_lat(22.77);
			location1.setGps_long(127.23);

			Location location2 = new Location(user1, "The Player Climbing Gym", Location.Types.GYM_GENERATED);

			Location location3 = new Location(user3, "Granite Peak", Location.Types.USER_GENERATED);
			location3.setCountry("USA");
			location3.setRegion("California");
			location3.setGps_lat(36.77);
			location3.setGps_long(-119.41);

			Location location4 = new Location(user4, "Urban Climb Gym", Location.Types.GYM_GENERATED);
			location4.setCountry("Australia");
			location4.setRegion("Brisbane");
			location4.setGps_lat(-27.47);
			location4.setGps_long(153.02);

			locationRepository.save(location1);
			locationRepository.save(location2);
			locationRepository.save(location3);
			locationRepository.save(location4);

			// Create Feature Tags
			List<FeatureTag> features = new ArrayList<>();
			features.add(new FeatureTag("crimpy"));
			features.add(new FeatureTag("dynamic"));
			features.add(new FeatureTag("static"));
			featureTagRepository.saveAll(features);

			List<FeatureTag> moreFeatures = new ArrayList<>();
			moreFeatures.add(new FeatureTag("overhang"));
			moreFeatures.add(new FeatureTag("compression"));
			moreFeatures.add(new FeatureTag("mantle"));
			moreFeatures.add(new FeatureTag("sloper"));
			featureTagRepository.saveAll(moreFeatures);

			// Create Climbs
			Climb climb1 = new Climb(user1, location1, "V3", Climb.Scales.V_SCALE, ZonedDateTime.now(), 3, true);
			climb1.setBoulder_name("Valhalla High Start");
			climb1.setAttempts(6);
			climb1.setComment("Though start, sketchy ending");
			climb1.setFeatures(features);
			climbRepository.save(climb1);

			Climb climb2 = new Climb(user2, location2, "V5", Climb.Scales.V_SCALE, ZonedDateTime.now().minusDays(2), 2, false);
			climb2.setBoulder_name("The Juggernaut");
			climb2.setAttempts(4);
			climb2.setComment("Powerful moves, couldn't stick the top.");
			climb2.setFeatures(moreFeatures);
			climbRepository.save(climb2);

			Climb climb3 = new Climb(user3, location3, "V2", Climb.Scales.V_SCALE, ZonedDateTime.now().minusDays(5), 1, true);
			climb3.setBoulder_name("Granite Groove");
			climb3.setAttempts(2);
			climb3.setComment("Nice flow, good holds.");
			climb3.setFeatures(Stream.concat(features.stream().limit(2),moreFeatures.stream().limit(2)).collect(Collectors.toList())); // reuse earlier features
			climbRepository.save(climb3);
			
	      };
	}
	
}
