package com.gor.socialmediarest.testutils;

import com.gor.socialmediarest.requests.UpdateUserRequest;
import org.springframework.stereotype.Component;

@Component
public class UpdateUserRequestFactory {
    public UpdateUserRequest createRequest() {
        return new UpdateUserRequest("name", "username");
    }
}
