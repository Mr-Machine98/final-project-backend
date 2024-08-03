package com.mrmachine.apifinalapp.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mrmachine.apifinalapp.repositories.UserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

	private UserRepository userRepo;
	
	public JpaUserDetailsService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<com.mrmachine.apifinalapp.models.auth.User> userOpt = this.userRepo.findByUsername(username);

		if (!userOpt.isPresent()) {
			throw new UsernameNotFoundException(String.format("Username %s not exists.", username));
		}
		
		com.mrmachine.apifinalapp.models.auth.User user = userOpt.orElseThrow();
		
		List<GrantedAuthority> authorities = user
				.getRoles()
				.stream()
				.map( r -> new SimpleGrantedAuthority(r.getName()))
				.collect(Collectors.toList());
		
		return new User(
				user.getUsername(),
				user.getPassword(),
				true,
				true,
				true,
				true,
				authorities
		);
	}

}
