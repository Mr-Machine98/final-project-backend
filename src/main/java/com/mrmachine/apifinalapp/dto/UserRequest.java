package com.mrmachine.apifinalapp.dto;

import com.mrmachine.apifinalapp.models.auth.IUser;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserRequest implements IUser {
	@NotEmpty
	@Size(min = 4, max = 8)
	@Column(unique = true)
	private String username;
	
	@NotBlank
	@Email
	@Column(unique = true)
	private String email;
	
	private Boolean isAdmin;
}
