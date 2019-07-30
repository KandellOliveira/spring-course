package com.example.springcourse.dto;

import javax.validation.constraints.NotNull;

import com.example.springcourse.domain.enums.Role;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserUpdateRoledto {

	@NotNull(message = "Role requeired")
	private Role role;
}
