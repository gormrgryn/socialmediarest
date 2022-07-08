package com.gor.socialmediarest.testutils;

import com.gor.socialmediarest.db.entities.UserEntity;

public class UserFactory {

    public UserEntity createEntity() {
        return new UserEntity("name", "username", "password");
    }
    
}
