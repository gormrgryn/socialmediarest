package com.gor.socialmediarest.testutils;

import com.gor.socialmediarest.requests.PostRequest;
import org.springframework.stereotype.Component;

@Component
public class PostRequestFactory {
    public PostRequest createRequest() {
        return new PostRequest("post text from request");
    }
}
