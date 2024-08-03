package com.mrmachine.apifinalapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrmachine.apifinalapp.dto.UserDTO;
import com.mrmachine.apifinalapp.dto.UserRequest;
import com.mrmachine.apifinalapp.dto.mapper.DtoMapperUser;
import com.mrmachine.apifinalapp.models.auth.IUser;
import com.mrmachine.apifinalapp.models.auth.Role;
import com.mrmachine.apifinalapp.models.auth.User;
import com.mrmachine.apifinalapp.repositories.RoleRepository;
import com.mrmachine.apifinalapp.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	private UserRepository userRepo;
	private PasswordEncoder passwordEncoder;
	private RoleRepository roleRepo;
	
	public UserServiceImpl(UserRepository userRepo, PasswordEncoder passwordEncoder, RoleRepository roleRepo) {
		super();
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
		this.roleRepo = roleRepo;
	}

	@Override
	public List<UserDTO> findAll() {
		List<UserDTO> dtos = new ArrayList<UserDTO>();
		
		this.userRepo.findAll().forEach( user -> {
			dtos.add(DtoMapperUser
				.builder()
				.setUser(user)
				.build()
			);
		});
		return dtos;
	}

	@Override
	public Optional<UserDTO> findById(Long id) {
		Optional<User> u = this.userRepo.findById(id);
		if (u.isPresent()) {
			return Optional.of(DtoMapperUser
				.builder()
				.setUser(u.orElseThrow())
				.build()
			);
		} else {
			return Optional.empty();
		}		
	}

	@Override
	@Transactional
	public UserDTO save(User u) {
		List<Role> roles = getRoles(u);
		u.setRoles(roles);
		u.setPassword(this.passwordEncoder.encode(u.getPassword()));
		
		return DtoMapperUser
				.builder()
				.setUser(this.userRepo.save(u))
				.build();
	}

	@Override
	@Transactional
	public void remove(Long id) {
		this.userRepo.deleteById(id);
	}

	@Override
	@Transactional
	public Optional<UserDTO> update(UserRequest u, Long id) {
		
		Optional<User> optUser = this.userRepo.findById(id);
		
		User user = null;
		
		if (optUser.isPresent()) {
			List<Role> roles = getRoles(u);
			User currentUser = optUser.orElseThrow();
			currentUser.setUsername(u.getUsername());
			currentUser.setEmail(u.getEmail());
			currentUser.setRoles(roles);
			user = this.userRepo.save(currentUser);
		}
		
		return Optional.ofNullable(DtoMapperUser
				.builder()
				.setUser(user)
				.build()
		);
	}
	
	private List<Role> getRoles(IUser u){
		List<Role> roles = new ArrayList<>();
		Optional<Role> roleUserOpt = this.roleRepo.findByName("ROLE_USER");
		
		if (roleUserOpt.isPresent()) {			
			roles.add(roleUserOpt.orElseThrow());
		}
		
		if(u.getIsAdmin()) {
			Optional<Role> roleAdminOpt = this.roleRepo.findByName("ROLE_ADMIN");
			if (roleAdminOpt.isPresent()) {
				roles.add(roleAdminOpt.orElseThrow());
			}
		}
		return roles;
	}

	@Override
	public Page<UserDTO> findAll(Pageable pageable) {
		return this.userRepo
				.findAll(pageable)
				.map(u -> DtoMapperUser.builder().setUser(u).build());
	}

}
