package com.gor.socialmediarest.utils.mappers;

import com.gor.socialmediarest.db.entities.UserRoleEntity;
import com.gor.socialmediarest.dto.UserRoleDto;
import org.springframework.stereotype.Component;

@Component
public class UserRoleMapper {
	public UserRoleDto toDto(UserRoleEntity userRole) {
		return new UserRoleDto(
			userRole.getId(),
			userRole.getName().name()
		);
	}
}
