package com.mrmachine.apifinalapp.auth;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.mrmachine.apifinalapp.auth.filters.JwtAuthenticationFilter;
import com.mrmachine.apifinalapp.auth.filters.JwtValidationFilter;

@Configuration
public class SpringSecurityConfig {
	
	private AuthenticationConfiguration authenticationConfiguration;
	
	public SpringSecurityConfig(AuthenticationConfiguration authenticationConfiguration) {
		this.authenticationConfiguration = authenticationConfiguration;
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.authorizeHttpRequests( requestMatcher -> 
			requestMatcher
				.requestMatchers(HttpMethod.GET, "/api/final-app/all").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/final-app/users/all").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/final-app/users/{id}").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/final-app/users/page/{page}").permitAll()
				.requestMatchers(HttpMethod.POST, "/api/final-app/addsales").permitAll()
				.requestMatchers(HttpMethod.POST, "/api/final-app/users").hasAnyRole("ADMIN")				
				.requestMatchers(HttpMethod.PUT, "/api/final-app/users/{id}").hasAnyRole("ADMIN")				
				.requestMatchers(HttpMethod.DELETE, "/api/final-app/users/{id}").hasAnyRole("ADMIN")				
				.anyRequest()
				.authenticated()
		)
		.addFilter(new JwtAuthenticationFilter(this.authenticationConfiguration.getAuthenticationManager()))
		.addFilter(new JwtValidationFilter(this.authenticationConfiguration.getAuthenticationManager()))
		.csrf( config -> config.disable())
		.sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.cors(cors -> cors.configurationSource(corsConfigurationSource()))
		.build();
	}
	
	@Bean
	AuthenticationManager authenticationManager() throws Exception {
		return this.authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
		config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
		config.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		
		return source;
	}
	
	@Bean
	FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
