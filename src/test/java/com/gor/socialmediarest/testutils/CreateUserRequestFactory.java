package com.gor.socialmediarest.testutils;

import com.gor.socialmediarest.requests.CreateUserRequest;
import org.springframework.stereotype.Component;

@Component
public class CreateUserRequestFactory {
    public CreateUserRequest createRequest() {
        return new CreateUserRequest("name", "username", "password");
    }
}
