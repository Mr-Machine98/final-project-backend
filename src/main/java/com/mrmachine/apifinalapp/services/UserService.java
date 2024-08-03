package com.mrmachine.apifinalapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mrmachine.apifinalapp.dto.UserDTO;
import com.mrmachine.apifinalapp.dto.UserRequest;
import com.mrmachine.apifinalapp.models.auth.User;

public interface UserService {
	List<UserDTO> findAll();
	Page<UserDTO> findAll(Pageable pageable);
	Optional<UserDTO> findById(Long id);
	UserDTO save(User u);
	Optional<UserDTO> update(UserRequest u, Long id);
	void remove(Long id);
}
