package com.gor.socialmediarest.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import com.gor.socialmediarest.db.entities.UserEntity;
import com.gor.socialmediarest.db.entities.UserRoleEntity;
import com.gor.socialmediarest.db.entities.PostEntity;
import com.gor.socialmediarest.db.repositories.UserRepository;
import com.gor.socialmediarest.db.repositories.UserRoleRepository;
import com.gor.socialmediarest.dto.PostDto;
import com.gor.socialmediarest.dto.UserDto;
import com.gor.socialmediarest.dto.UserRoleDto;
import com.gor.socialmediarest.requests.CreateUserRequest;
import com.gor.socialmediarest.requests.UpdateUserRequest;
import com.gor.socialmediarest.security.UserRole;
import com.gor.socialmediarest.testutils.CreateUserRequestFactory;
import com.gor.socialmediarest.testutils.PostFactory;
import com.gor.socialmediarest.testutils.UpdateUserRequestFactory;
import com.gor.socialmediarest.testutils.UserFactory;
import com.gor.socialmediarest.testutils.UserRoleFactory;
import com.gor.socialmediarest.utils.mappers.*;
import com.gor.socialmediarest.utils.validation.RequestValidationUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.BDDMockito.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RequestValidationUtil requestValidationUtil;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PostMapper postMapper;
    @Mock
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserFactory userFactory;
    @Autowired
    private PostFactory postFactory;
    @Autowired
    private UserRoleFactory userRoleFactory;
    @Autowired
    private CreateUserRequestFactory createUserRequestFactory;
    @Autowired
    private UpdateUserRequestFactory updateUserRequestFactory;
    private UserService userService;
    
    @BeforeEach
    public void setUp() {
        userService = new UserService(userRepository, requestValidationUtil, userMapper, postMapper, userRoleRepository, userRoleMapper);
    }
    
    /**
     * Test of fetchById method, of class UserService.
     */
    @Test
    public void testFetchById() {
        System.out.println("fetchById");
        UserEntity user = userFactory.createEntity();
        long id = user.getId();
        given(userRepository.findById(id)).willReturn(Optional.of(user));
        UserDto expResult = userMapper.toDto(user);
        UserDto result = userService.fetchById(id);
        
        assertThat(result).isEqualTo(expResult);
    }

    /**
     * Test of fetchAllUsers method, of class UserService.
     */
    @Test
    public void testFetchAllUsers() {
        System.out.println("fetchAllUsers");
        UserEntity user = userFactory.createEntity();
        
        given(userRepository.findAll()).willReturn(Arrays.asList(user));
        List<UserDto> expResult;
        expResult = Arrays.asList(user)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        List<UserDto> result = userService.fetchAllUsers();
        assertThat(result).isEqualTo(expResult);
    }

    /**
     * Test of loadUserByUsername method, of class UserService.
     */
    @Test
    public void testLoadUserByUsername() {
        System.out.println("loadUserByUsername");
        UserEntity user = userFactory.createEntity();
        String username = user.getUsername();
        given(userRepository.findByUsername(username)).willReturn(Optional.of(user));
        UserDetails expResult = user;
        UserDetails result = userService.loadUserByUsername(username);
        
        assertThat(result).isEqualTo(expResult);
    }

    /**
     * Test of fetchByUsername method, of class UserService.
     */
    @Test
    public void testFetchByUsername() {
        System.out.println("fetchByUsername");
        UserEntity user = userFactory.createEntity();
        String username = user.getUsername();
        given(userRepository.findByUsername(username)).willReturn(Optional.of(user));
        UserDto expResult = userMapper.toDto(user);
        UserDto result = userService.fetchByUsername(username);
        
        assertThat(result).isEqualTo(expResult);
    }

    /**
     * Test of fetchUserPosts method, of class UserService.
     */
    @Test
    public void testFetchUserPosts() {
        System.out.println("fetchUserPosts");
        UserEntity user = userFactory.createEntity();
        long id = user.getId();
        PostEntity post = postFactory.createEntity();
        post.setAuthor(user);
        List<PostEntity> posts = Arrays.asList(post);
        user.setPosts(posts);
        given(userRepository.findById(id)).willReturn(Optional.of(user));
        List<PostDto> expResult = posts
                .stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
        List<PostDto> result = userService.fetchUserPosts(id);
        assertThat(result).isEqualTo(expResult);
    }

    /**
     * Test of createUser method, of class UserService.
     * @throws java.lang.Exception
     */
    @Test
    public void testCreateUser() throws Exception {
        System.out.println("createUser");
        CreateUserRequest request = createUserRequestFactory.createRequest();
        UserRoleEntity role = userRoleFactory.createEntity();
        UserEntity user = userMapper.toEntity(request);
        user.addRole(role);
        
        doNothing().when(requestValidationUtil).checkRegisterUserRequestValidation(request);
        given(userRoleRepository.findByName(UserRole.USER)).willReturn(Optional.of(role));
        userService.createUser(request);
        
        ArgumentCaptor<UserEntity> argumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(argumentCaptor.capture());
        assertThat(user.getName()).isEqualTo(argumentCaptor.getValue().getName());
        assertThat(user.getUsername()).isEqualTo(argumentCaptor.getValue().getUsername());
        assertThat(user.getRoles()).isEqualTo(argumentCaptor.getValue().getRoles());
    }

    /**
     * Test of updateUser method, of class UserService.
     * @throws java.lang.Exception
     */
    @Test
    public void testUpdateUser() throws Exception {
        System.out.println("updateUser");
        long id = 0L;
        UpdateUserRequest request = updateUserRequestFactory.createRequest();
        UserEntity user = new UserEntity("Different Name", "differentusername", "password");
        user.setId(id);
        doNothing().when(requestValidationUtil).checkUpdateUserRequestValidation(request);
        given(userRepository.findById(id)).willReturn(Optional.of(user));
        userService.updateUser(id, request);
        
        ArgumentCaptor<UserEntity> argumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(argumentCaptor.capture());
        assertThat(request.getName()).isEqualTo(argumentCaptor.getValue().getName());
        assertThat(request.getUsername()).isEqualTo(argumentCaptor.getValue().getUsername());
    }

    /**
     * Test of fetchRoles method, of class UserService.
     */
    @Test
    public void testFetchRoles() {
        System.out.println("fetchRoles");
        UserRoleEntity role = userRoleFactory.createEntity();
        given(userRoleRepository.findAll()).willReturn(
                Arrays.asList(role)
        );
        List<UserRoleDto> expResult = Arrays.asList(userRoleMapper.toDto(role));
        List<UserRoleDto> result = userService.fetchRoles();
        
        assertThat(result).isEqualTo(expResult);
    }
}
