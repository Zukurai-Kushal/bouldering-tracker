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
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fdm.bouldering_tracker.model.Climb;
import com.fdm.bouldering_tracker.model.FeatureTag;
import com.fdm.bouldering_tracker.model.Location;
import com.fdm.bouldering_tracker.model.Location.Types;
import com.fdm.bouldering_tracker.model.AppUser;
import com.fdm.bouldering_tracker.repository.ClimbRepository;
import com.fdm.bouldering_tracker.repository.FeatureTagRepository;
import com.fdm.bouldering_tracker.repository.LocationRepository;
import com.fdm.bouldering_tracker.repository.AppUserRepository;

@Configuration
@Profile("dev")
public class DataLoader {

    @Bean
    public CommandLineRunner dummyDataLoader(AppUserRepository userRepo,
                                             LocationRepository locationRepo,
                                             ClimbRepository climbRepo,
                                             FeatureTagRepository tagRepo,
                                             PasswordEncoder passwordEncoder) {
        return args -> {

            // Create Users
            List<AppUser> users = List.of(
                new AppUser("Alex", "alex@gmail.com", passwordEncoder.encode("pass123")),
                new AppUser("Jamie", "jamie@example.com", "hash_jamie"),
                new AppUser("Riley", "riley@example.com", "hash_riley"),
                new AppUser("Casey", "casey@example.com", "hash_casey"),
                new AppUser("Drew", "drew@example.com", "hash_drew")
            );
            userRepo.saveAll(users);

            // Create Locations with photo URLs
            List<Location> locations = List.of(
                new Location(users.get(1), "Sunset Forest", Location.Types.OUTDOOR, "Hong Kong SAR", "Ngau Chi Wan", 22.77, 127.23, "https://static.thehoneycombers.com/wp-content/uploads/sites/6/2022/05/climbing-gyms-lead-image.jpg"),
                new Location(users.get(0), "The Player Climbing Gym", Location.Types.INDOOR, "Hong Kong SAR", "Kowloon Bay", 22.32, 114.21, "https://hongkongliving.com/wp-content/uploads/2020/06/Hong-Kong-Climbing-Park-scaled-1-1026x1368.jpg"),
                new Location(users.get(2), "Granite Peak", Location.Types.OUTDOOR, "USA", "California", 36.77, -119.41, "https://shaleewanders.com/wp-content/uploads/2020/11/IMG_5943-2-1152x1536.jpg"),
                new Location(users.get(3), "Urban Climb Gym", Location.Types.INDOOR, "Australia", "Brisbane", -27.47, 153.02, "https://ek5jbm9x6ox.exactdn.com/wp-content/uploads/2021/08/19043347/Urban-Climb-Newstead2.jpg?lossy=1&ssl=1"),
                new Location(users.get(4), "Fontainebleau", Location.Types.OUTDOOR, "France", "Île-de-France", 48.40, 2.70, "https://images.unsplash.com/photo-1678285581926-66fc0a236382?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
                new Location(users.get(0), "Boulder Central", Location.Types.INDOOR, "UK", "Leicester", 52.63, -1.13, "https://plus.unsplash.com/premium_photo-1672280940830-7a25b0b5d3fb?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8Y2xpbWJpbmclMjBneW18ZW58MHx8MHx8&w=1000&q=80")
            );
            locationRepo.saveAll(locations);

            // Create Feature Tags
            List<FeatureTag> features = List.of(
                new FeatureTag("crimpy"),
                new FeatureTag("dynamic"),
                new FeatureTag("static"),
                new FeatureTag("overhang"),
                new FeatureTag("compression"),
                new FeatureTag("mantle"),
                new FeatureTag("sloper"),
                new FeatureTag("technical"),
                new FeatureTag("powerful"),
                new FeatureTag("balance")
            );
            tagRepo.saveAll(features);

            // Create Climbs using full constructor
            List<Climb> climbs = List.of(
                new Climb(users.get(0), locations.get(0), "V3", Climb.Scales.V_SCALE, ZonedDateTime.now(), 3, true, 6,
                    features.subList(0, 3), "Tough start, sketchy ending", "Valhalla High Start", "https://images.unsplash.com/photo-1520156557489-31c63271fcd4?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NXx8Y2xpbWJpbmclMjB3YWxsfGVufDB8fDB8fHww&w=1000&q=80"),

                new Climb(users.get(1), locations.get(1), "V5", Climb.Scales.V_SCALE, ZonedDateTime.now().minusDays(2), 2, false, 4,
                    features.subList(3, 6), "Powerful moves, couldn't stick the top.", "The Juggernaut", "https://plus.unsplash.com/premium_photo-1672281090596-d9e7735b0905?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),

                new Climb(users.get(2), locations.get(2), "V2", Climb.Scales.V_SCALE, ZonedDateTime.now().minusDays(5), 1, true, 2,
                    Stream.concat(features.stream().limit(2), features.stream().skip(6).limit(2)).collect(Collectors.toList()),
                    "Nice flow, good holds.", "Granite Groove", "https://plus.unsplash.com/premium_photo-1664304175916-bfb66c9850fe?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),

                new Climb(users.get(3), locations.get(3), "V6", Climb.Scales.V_SCALE, ZonedDateTime.now().minusDays(1), 3, true, 5,
                    features.subList(4, 8), "Compression and slopers, very physical.", "Brisbane Beast", "https://ek5jbm9x6ox.exactdn.com/wp-content/uploads/2021/08/19043347/Urban-Climb-Newstead2.jpg?lossy=1&ssl=1"),

                new Climb(users.get(4), locations.get(4), "6A", Climb.Scales.FONT_SCALE, ZonedDateTime.now().minusDays(3), 3, true, 3,
                    features.subList(7, 10), "Font-style technical climb with balance.", "Bleau Classic", "https://images.unsplash.com/photo-1678285581926-66fc0a236382?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),

                new Climb(users.get(0), locations.get(5), "V4", Climb.Scales.V_SCALE, ZonedDateTime.now().minusDays(7), 3, false, 7,
                    Stream.of(features.get(1), features.get(5)).collect(Collectors.toList()),
                    "Gym route with dynamic start and mantle finish.", "Central Circuit", "https://plus.unsplash.com/premium_photo-1672280940830-7a25b0b5d3fb?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8Y2xpbWJpbmclMjBneW18ZW58MHx8MHx8&w=1000&q=80")
            );

            climbRepo.saveAll(climbs);
        };
    }
}
