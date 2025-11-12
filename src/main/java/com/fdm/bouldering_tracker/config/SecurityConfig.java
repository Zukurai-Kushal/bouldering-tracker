package com.fdm.bouldering_tracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

	//CSRF Disabled (Using this for development only to make postman queries easier to implement)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF for REST APIs
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/users/register", "/swagger-ui/**", "/v3/api-docs/**","/public/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
//                .loginPage("/login") // Optional: custom login page
                .defaultSuccessUrl("/users/me", true)
                )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
			.logoutSuccessHandler((request, response, authentication) -> {
			        response.setStatus(HttpServletResponse.SC_OK);
			    })
			.permitAll()
            );

        return http.build();
    }
    
//    //CSRF Enabled
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//            .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/login", "/users/register", "/swagger-ui/**", "/v3/api-docs/**", "/public/**").permitAll()
//                .anyRequest().authenticated()
//            )
//            .formLogin(form -> form.defaultSuccessUrl("/users/me", true))
//            .logout(logout -> logout
//                .logoutUrl("/logout")
//                .logoutSuccessHandler((request, response, authentication) -> response.setStatus(HttpServletResponse.SC_OK))
//                .permitAll()
//            );
//        return http.build();
//    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}