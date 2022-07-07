package com.gor.socialmediarest.services;

import com.gor.socialmediarest.db.entities.PostEntity;
import com.gor.socialmediarest.db.repositories.PostRepository;
import com.gor.socialmediarest.dto.PostDto;
import com.gor.socialmediarest.requests.PostRequest;
import com.gor.socialmediarest.utils.mappers.PostMapper;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private static final String ENTITY_NOT_FOUND_MSG = "Post with id %d not found";

    @Autowired
    public PostService(PostRepository postRepository, PostMapper postMapper, UserService userService) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.userService = userService;
    }
    
    private final UserService userService;

    public List<PostDto> fetchAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

    public PostDto fetchPostById(long id) throws EntityNotFoundException, Throwable {
        return postMapper.toDto(
                (PostEntity) postRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MSG, id))
                )
        );
    }

    public void createPost(PostRequest request) {
        PostEntity post = postMapper.toEntity(request);
        post.setAuthor(userService.loadAuthenticatedUser());
        postRepository.save(post);
    }
    
    public void updatePost(long id, PostRequest request) {
        PostEntity post = (PostEntity) postRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MSG, id))
        );
        if (!userService.loadAuthenticatedUser().getPosts().contains(post))
            throw new IllegalArgumentException("Access denied");
        
        post.setText(request.getText());
        postRepository.save(post);
    }
    
    public void deletePost(long id) {
        postRepository.deleteById(id);
    }
}
