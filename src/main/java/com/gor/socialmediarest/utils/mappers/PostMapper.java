package com.gor.socialmediarest.utils.mappers;

import com.gor.socialmediarest.db.entities.PostEntity;
import com.gor.socialmediarest.dto.PostDto;
import com.gor.socialmediarest.requests.PostRequest;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostDto toDto(PostEntity post) {
        return new PostDto(
                post.getId(),
                post.getText(),
                post.getLikesCount(),
                post.getCreatedDate(),
                post.getLastModifiedDate()
        );
    }
    
    public PostEntity toEntity(PostRequest post) {
        return new PostEntity(post.getText());
    }
}
