package com.promptoven.authservice.application.service.springsecurity.basic;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class SecurityConfig {

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(13);
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedOrigins(List.of(
			"*"));
		config.addAllowedMethod("*");
		config.addAllowedHeader("*");
		config.setExposedHeaders(List.of("Authorization"));
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(
				authorizeRequests -> authorizeRequests
					.requestMatchers(
						"/acutator/**",
						"/swagger-ui/**",
						"/v3/api-docs/**",
						"/swagger-ui.html",
						"/swagger-ui/**",
						"/v1/auth/**"
					)
					.permitAll()
					.anyRequest()
					.authenticated()
			)
			.sessionManagement(
				sessionManagement -> sessionManagement
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.addFilter(corsFilter());
		return http.build();
	}
}
