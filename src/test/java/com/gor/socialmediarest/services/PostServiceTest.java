package com.gor.socialmediarest.services;

import java.util.Arrays;
import java.util.List;

import com.gor.socialmediarest.db.entities.PostEntity;
import com.gor.socialmediarest.db.entities.UserEntity;
import com.gor.socialmediarest.db.repositories.PostRepository;
import com.gor.socialmediarest.dto.PostDto;
import com.gor.socialmediarest.requests.PostRequest;
import com.gor.socialmediarest.testutils.PostFactory;
import com.gor.socialmediarest.testutils.PostRequestFactory;
import com.gor.socialmediarest.testutils.UserFactory;
import com.gor.socialmediarest.utils.mappers.PostMapper;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.ArgumentCaptor;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PostServiceTest {
    private PostService postService;
    @Mock
    private PostRepository postRepository;
    @Autowired
    private PostMapper postMapper;
    @Mock
    private UserService userService;
    @Autowired
    private PostFactory postFactory;
    @Autowired
    private PostRequestFactory postRequestFactory;
    @Autowired
    private UserFactory userFactory;
    
    @BeforeEach
    public void setUp() {
        postService = new PostService(postRepository, postMapper, userService);
    }

    /**
     * Test of fetchAllPosts method, of class PostService.
     */
    @Test
    public void testFetchAllPosts() {
        System.out.println("fetchAllPosts");
        PostEntity post = postFactory.createEntity();
        given(postRepository.findAll()).willReturn(Arrays.asList(post));
        List<PostDto> expResult = Arrays.asList(postMapper.toDto(post));
        List<PostDto> result = postService.fetchAllPosts();
        
        assertThat(result).isEqualTo(expResult);
    }

    /**
     * Test of fetchPostById method, of class PostService.
     * @throws java.lang.Exception
     */
    @Test
    public void testFetchPostById() throws Exception, Throwable {
        System.out.println("fetchPostById");
        PostEntity post = postFactory.createEntity();
        long id = post.getId();
        given(postRepository.findById(id)).willReturn(Optional.of(post));
        PostDto expResult = postMapper.toDto(post);
        PostDto result = postService.fetchPostById(id);
        
        assertThat(result).isEqualTo(expResult);
    }

    /**
     * Test of createPost method, of class PostService.
     */
    @Test
    public void testCreatePost() {
        System.out.println("createPost");
        PostRequest request = postRequestFactory.createRequest();
        given(userService.loadAuthenticatedUser()).willReturn(userFactory.createEntity());
        postService.createPost(request);
        
        ArgumentCaptor<PostEntity> argumentCaptor = ArgumentCaptor.forClass(PostEntity.class);
        verify(postRepository).save(argumentCaptor.capture());
        assertThat(request.getText()).isEqualTo(argumentCaptor.getValue().getText());
    }

    /**
     * Test of updatePost method, of class PostService.
     */
    @Test
    public void testUpdatePost() {
        System.out.println("updatePost");
        PostRequest request = postRequestFactory.createRequest();
        PostEntity post = postFactory.createEntity();
        long id = post.getId();
        UserEntity user = userFactory.createEntity();
        user.setPosts(Arrays.asList(post));
        post.setAuthor(user);
        given(postRepository.findById(id)).willReturn(Optional.of(post));
        given(userService.loadAuthenticatedUser()).willReturn(user);
        postService.updatePost(id, request);
        
        ArgumentCaptor<PostEntity> argumentCaptor = ArgumentCaptor.forClass(PostEntity.class);
        verify(postRepository).save(argumentCaptor.capture());
        assertThat(request.getText()).isEqualTo(argumentCaptor.getValue().getText());
    }
}
