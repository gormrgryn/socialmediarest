package com.gor.socialmediarest.controllers;

import com.gor.socialmediarest.dto.UserDto;
import com.gor.socialmediarest.requests.CreateUserRequest;
import com.gor.socialmediarest.requests.UpdateUserRequest;
import com.gor.socialmediarest.services.UserService;
import com.gor.socialmediarest.utils.ResponseBuilder;
import com.gor.socialmediarest.utils.exceptions.InvalidNameException;
import com.gor.socialmediarest.utils.exceptions.InvalidPasswordException;
import com.gor.socialmediarest.utils.exceptions.InvalidUsernameException;
import java.time.format.DateTimeParseException;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.AllArgsConstructor;

import java.util.Objects;
import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final ResponseBuilder responseBuilder;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id) {
        UserDto userDto = userService.fetchById(id);
        return responseBuilder.buildResponse("User is fetched successfully", HttpStatus.OK, userDto);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getUserPosts(@PathVariable long id) {
        return responseBuilder.buildResponse("Posts are fetched successfully", HttpStatus.OK, userService.fetchUserPosts(id));
    }

    @GetMapping("/roles")
    public ResponseEntity<?> getRoles() {
        return responseBuilder.buildResponse("Roles are fetched successfully", HttpStatus.OK,
                userService.fetchRoles()
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getUsers() {
        return responseBuilder.buildResponse("Users are fetched successfully", HttpStatus.OK,
                userService.fetchAllUsers()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody UpdateUserRequest request) throws InvalidNameException, InvalidUsernameException, RuntimeException, IllegalArgumentException {
        if (!Objects.equals(id, userService.loadAuthenticatedUser().getId())) {
            responseBuilder.buildResponse("Permission denied", HttpStatus.FORBIDDEN);
        }
        userService.updateUser(id, request);
        return responseBuilder.buildResponse("User updated successfully", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) throws InvalidPasswordException, IllegalArgumentException, RuntimeException, DateTimeParseException, EntityNotFoundException, InvalidUsernameException, InvalidNameException {
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            throw new RuntimeException("First log out");
        }
        userService.createUser(request);
        return responseBuilder.buildResponse("User registered successfully", HttpStatus.OK);
    }
}
