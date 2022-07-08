package com.gor.socialmediarest.utils.mappers;

import com.gor.socialmediarest.db.entities.PostEntity;
import com.gor.socialmediarest.dto.PostDto;
import com.gor.socialmediarest.requests.PostRequest;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class PostMapper {

    private final UserMapper userMapper;

    @Autowired
    public PostMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public PostDto toDto(PostEntity post) {
        return new PostDto(
                post.getId(),
                post.getText(),
                post.getCreatedDate(),
                post.getLastModifiedDate(),
                userMapper.toDto(post.getAuthor())
        );
    }
    
    public PostEntity toEntity(PostRequest post) {
        return new PostEntity(post.getText());
    }
}
