package com.gor.socialmediarest.testutils;

import com.gor.socialmediarest.db.entities.PostEntity;
import org.springframework.stereotype.Component;

@Component
public class PostFactory {
    public PostEntity createEntity() {
        PostEntity post = new PostEntity("post");
        post.setId(1L);
        return post;
    }
}
