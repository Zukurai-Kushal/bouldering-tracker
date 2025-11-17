package com.fdm.bouldering_tracker.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf().disable().cors().and()
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/login", "/users/register", "/swagger-ui/**", "/v3/api-docs/**", "/public/**")
						.permitAll().anyRequest().authenticated())
				.formLogin(form -> form.successHandler((req, res, auth) -> {
					res.setStatus(HttpServletResponse.SC_OK);
					res.setContentType("application/json");
					res.getWriter().write("{\"message\":\"Login successful\"}");
				}).failureHandler((req, res, ex) -> {
					res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					res.setContentType("application/json");
					res.getWriter().write("{\"error\":\"Invalid username or password\"}");
				})).logout(logout -> logout.logoutSuccessHandler((req, res, auth) -> {
					res.setStatus(HttpServletResponse.SC_OK);
					res.setContentType("application/json");
					res.getWriter().write("{\"message\":\"Logout successful\"}");
				})).exceptionHandling(ex -> ex.authenticationEntryPoint((req, res, authException) -> {
					res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					res.setContentType("application/json");
					res.getWriter().write("{\"error\":\"Not authenticated\"}");
				}));

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOriginPatterns(List.of("http://localhost:*")); // Dev origin
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}