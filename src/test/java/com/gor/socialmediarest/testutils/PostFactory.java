package com.gor.socialmediarest.testutils;

import com.gor.socialmediarest.db.entities.PostEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostFactory {
    
    private final UserFactory userFactory;

    @Autowired
    public PostFactory(UserFactory userFactory) {
        this.userFactory = userFactory;
    }
    
    public PostEntity createEntity() {
        PostEntity post = new PostEntity("post text from factory");
        post.setId(1L);
        post.setAuthor(userFactory.createEntity());
        return post;
    }
}
