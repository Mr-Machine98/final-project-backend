package com.mrmachine.apifinalapp.dto.mapper;

import com.mrmachine.apifinalapp.dto.UserDTO;
import com.mrmachine.apifinalapp.models.auth.User;

public class DtoMapperUser {

	private User user;
	
	private DtoMapperUser() {}
	
	public static DtoMapperUser builder() {
		return new DtoMapperUser();
	}
	
	public DtoMapperUser setUser(User u) {
		this.user = u;
		return this;
	}
	
	public UserDTO build() {
		if (this.user == null) {
			throw new RuntimeException("Verify that user is not null");
		}
		
		Boolean isAdmin = this.user.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_ADMIN"));
		return new UserDTO(
				this.user.getId(),
				this.user.getUsername(),
				this.user.getEmail(),
				isAdmin
			);
	}
	
}
