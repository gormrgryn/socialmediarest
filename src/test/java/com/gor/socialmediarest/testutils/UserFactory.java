package com.gor.socialmediarest.testutils;

import com.gor.socialmediarest.db.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {

    public UserEntity createEntity() {
        UserEntity user = new UserEntity("name", "username", "password");
        user.setId(1L);
        return user;
    }
    
}
