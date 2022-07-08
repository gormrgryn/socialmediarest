package com.gor.socialmediarest.testutils;

import com.gor.socialmediarest.db.entities.PostEntity;
import org.springframework.stereotype.Component;

@Component
public class PostFactory {
    public PostEntity createEntity() {
        return new PostEntity("post");
    }
}
