package com.gor.socialmediarest.db.repositories;

import com.gor.socialmediarest.TestConfig;
import com.gor.socialmediarest.db.entities.PostEntity;
import com.gor.socialmediarest.testutils.PostFactory;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(TestConfig.class)
public class PostRepositoryTest {

    private final PostRepository postRepository;
    private final PostFactory postFactory;
    private final UserRepository userRepository;

    @Autowired
    public PostRepositoryTest(PostRepository postRepository, PostFactory postFactory, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postFactory = postFactory;
        this.userRepository = userRepository;
    }

    @AfterEach
    public void tearDown() {
        postRepository.deleteAll();
    }

    @Test
    public void deletePostById_Success() {
        PostEntity post = postFactory.createEntity();
        post = save(post);
        postRepository.deleteById(post.getId());

        assertThat(postRepository.findAll()).isEmpty();
    }

    @Test
    public void deleteAllPosts_Success() {
        PostEntity post = postFactory.createEntity();
        save(post);
        postRepository.deleteAll();

        assertThat(postRepository.findAll()).isEmpty();
    }

    @Test
    public void savePost_Success() {
        PostEntity post = postFactory.createEntity();
        post = save(post);

        assertThat(postRepository.findAll()).contains(post);
    }

    @Test
    public void findPostById_Success() {
        PostEntity post = postFactory.createEntity();
        post = save(post);

        assertThat(postRepository.findById(post.getId())).isEqualTo(Optional.of(post));
    }

    @Test
    public void deletePostById_Failure() {
        assertThatThrownBy(() -> postRepository.deleteById(10L))
                .isInstanceOf(org.springframework.dao.EmptyResultDataAccessException.class);
    }
    
    private PostEntity save(PostEntity post) {
        post.setAuthor(userRepository.save(post.getAuthor()));
        return postRepository.save(post);
    }

}
