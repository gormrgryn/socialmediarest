package com.gor.socialmediarest.db.repositories;

import com.gor.socialmediarest.TestConfig;
import com.gor.socialmediarest.db.entities.UserEntity;
import com.gor.socialmediarest.testutils.UserFactory;
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
public class UserRepositoryTest {

    private final UserRepository userRepository;
    private final UserFactory userFactory;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository, UserFactory userFactory) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void deleteUserById_Success() {
        UserEntity user = userFactory.createEntity();
        user = userRepository.save(user);
        userRepository.deleteById(user.getId());

        assertThat(userRepository.findAll()).isEmpty();
    }

    @Test
    public void deleteAllUsers_Success() {
        UserEntity user = userFactory.createEntity();
        userRepository.save(user);
        userRepository.deleteAll();

        assertThat(userRepository.findAll()).isEmpty();
    }

    @Test
    public void saveUser_Success() {
        UserEntity user = userFactory.createEntity();
        user = userRepository.save(user);

        assertThat(userRepository.findAll()).contains(user);
    }

    @Test
    public void findUserById_Success() {
        UserEntity user = userFactory.createEntity();
        user = userRepository.save(user);

        assertThat(userRepository.findById(user.getId())).isEqualTo(Optional.of(user));
    }

    @Test
    public void deleteUserById_Failure() {
        assertThatThrownBy(() -> userRepository.deleteById(10L))
                .isInstanceOf(org.springframework.dao.EmptyResultDataAccessException.class);
    }

}
