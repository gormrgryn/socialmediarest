package com.gor.socialmediarest.utils;

import com.gor.socialmediarest.db.entities.UserRoleEntity;
import com.gor.socialmediarest.db.repositories.UserRoleRepository;
import com.gor.socialmediarest.security.UserRole;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TablesManagementUtil {
	@Autowired
	private final UserRoleRepository userRoleRepository;

	@PostConstruct
	public void initRolesTable() {
		if (!userRoleRepository.findAll().isEmpty()) return;
		Arrays.asList(UserRole.values()).stream().forEach(r -> userRoleRepository.save(new UserRoleEntity(r)));
	}
}
