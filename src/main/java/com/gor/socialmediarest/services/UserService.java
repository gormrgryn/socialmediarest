package com.gor.socialmediarest.services;

import com.gor.socialmediarest.db.entities.UserEntity;
import com.gor.socialmediarest.db.repositories.UserRepository;
import com.gor.socialmediarest.db.repositories.UserRoleRepository;
import com.gor.socialmediarest.dto.UserDto;
import com.gor.socialmediarest.dto.PostDto;
import com.gor.socialmediarest.dto.UserRoleDto;
import com.gor.socialmediarest.requests.CreateUserRequest;
import com.gor.socialmediarest.requests.UpdateUserRequest;
import com.gor.socialmediarest.security.UserRole;
import com.gor.socialmediarest.utils.exceptions.InvalidNameException;
import com.gor.socialmediarest.utils.exceptions.InvalidPasswordException;
import com.gor.socialmediarest.utils.exceptions.InvalidUsernameException;
import com.gor.socialmediarest.utils.mappers.UserMapper;
import com.gor.socialmediarest.utils.mappers.PostMapper;
import com.gor.socialmediarest.utils.mappers.UserRoleMapper;
import com.gor.socialmediarest.utils.validation.RequestValidationUtil;

import java.util.List;
import java.util.stream.Collectors;
import java.time.format.DateTimeParseException;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RequestValidationUtil requestValidationUtil;
    private final UserMapper userMapper;
    private final PostMapper postMapper;
    private final UserRoleRepository userRoleRepository;
    private final UserRoleMapper userRoleMapper;

    @Autowired
    public UserService(UserRepository userRepository, RequestValidationUtil requestValidationUtil, UserMapper userMapper, PostMapper postMapper, UserRoleRepository userRoleRepository, UserRoleMapper userRoleMapper) {
        this.userRepository = userRepository;
        this.requestValidationUtil = requestValidationUtil;
        this.userMapper = userMapper;
        this.postMapper = postMapper;
        this.userRoleRepository = userRoleRepository;
        this.userRoleMapper = userRoleMapper;
    }

    private final String USERNAME_NOT_FOUND_MSG = "User with username %s doesn't exist";
    private final String ENTITY_NOT_FOUND_MSG = "User with id %s doesn't exist";
    private final String USER_ROLE_NOT_FOUND = "UserRoleEntity with name %s is not found";

    public UserDto fetchById(long id) throws EntityNotFoundException {
        return userMapper.toDto(userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MSG, id))
        ));
    }

    public List<UserDto> fetchAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND_MSG, username))
        );
    }

    public UserDto fetchByUsername(String username) throws UsernameNotFoundException {
        return userMapper.toDto((UserEntity) loadUserByUsername(username));
    }

    public List<PostDto> fetchUserPosts(long id) {
        return userRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MSG, id))
        ).getPosts()
            .stream()
            .map(postMapper::toDto)
            .collect(Collectors.toList());
    }

    public UserEntity loadAuthenticatedUser() throws UsernameNotFoundException, AuthenticationCredentialsNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getName().equals("anonymousUser")) {
            throw new AuthenticationCredentialsNotFoundException("No authenticated user");
        }
        String username = auth.getName();
        return (UserEntity) loadUserByUsername(username);
    }

    public void createUser(CreateUserRequest request) throws DateTimeParseException, IllegalArgumentException, RuntimeException, EntityNotFoundException, InvalidUsernameException, InvalidNameException, InvalidPasswordException {
        requestValidationUtil.checkRegisterUserRequestValidation(request);
        checkRequestCredentialsUnique(request);
        UserEntity user = userMapper.toEntity(request);
        user.addRole(
                userRoleRepository.findByName(UserRole.USER).orElseThrow(
                        () -> new EntityNotFoundException(
                                String.format(USER_ROLE_NOT_FOUND, UserRole.USER.name())
                        )
                )
        );
        userRepository.save(user);
    }

    public void updateUser(long id, UpdateUserRequest request) throws DateTimeParseException, IllegalArgumentException, RuntimeException, EntityNotFoundException, InvalidUsernameException, InvalidNameException {
        requestValidationUtil.checkUpdateUserRequestValidation(request);
        UserEntity user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MSG, id))
        );
        checkRequestCredentialsUnique(request);
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        userRepository.save(user);
    }

    private void checkRequestCredentialsUnique(CreateUserRequest request)
            throws IllegalArgumentException {
        boolean usernameExists = userRepository.findByUsername(request.getUsername()).isPresent();
        if (usernameExists) {
            throw new IllegalArgumentException("Username is already taken");
        }
    }

    private void checkRequestCredentialsUnique(UpdateUserRequest request)
            throws IllegalArgumentException {
        boolean usernameExists = userRepository.findByUsername(request.getUsername()).isPresent();
        if (usernameExists) {
            throw new IllegalArgumentException("Username is already taken");
        }
    }

    public List<UserRoleDto> fetchRoles() {
        return userRoleRepository.findAll().stream()
                .map(userRoleMapper::toDto)
                .collect(Collectors.toList());
    }
}
