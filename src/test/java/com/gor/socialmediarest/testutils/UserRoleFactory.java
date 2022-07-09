package com.gor.socialmediarest.testutils;

import com.gor.socialmediarest.db.entities.UserRoleEntity;
import com.gor.socialmediarest.security.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserRoleFactory {
    public UserRoleEntity createEntity() {
        UserRoleEntity role = new UserRoleEntity(1L, UserRole.USER, null, null, null);
        return role;
    }
}
