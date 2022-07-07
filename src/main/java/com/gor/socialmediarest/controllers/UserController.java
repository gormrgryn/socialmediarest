package com.gor.socialmediarest.controllers;

import com.gor.socialmediarest.dto.UserDto;
import com.gor.socialmediarest.requests.CreateUserRequest;
import com.gor.socialmediarest.requests.UpdateUserRequest;
import com.gor.socialmediarest.services.UserService;
import com.gor.socialmediarest.utils.ResponseBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.AllArgsConstructor;

import java.util.Objects;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final ResponseBuilder responseBuilder;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        UserDto userDto = userService.fetchById(id);
        return responseBuilder.buildResponse("Successful", HttpStatus.OK, userDto);
    }

    @GetMapping("/roles")
    public ResponseEntity<?> getRoles() {
        return responseBuilder.buildResponse("Roles are fetched successfully", HttpStatus.OK,
                userService.fetchRoles()
        );
    }

    @GetMapping
    public ResponseEntity<?> getUsers() {
        return responseBuilder.buildResponse("Successful", HttpStatus.OK,
                userService.fetchAllUsers()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        if (!Objects.equals(id, userService.loadAuthenticatedUser().getId())) {
            responseBuilder.buildResponse("Permission denied", HttpStatus.FORBIDDEN);
        }
        userService.updateUser(id, request);
        return responseBuilder.buildResponse("User updated successfully", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) {
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            throw new RuntimeException("First log out");
        }
        userService.registerUser(request);
        return responseBuilder.buildResponse("User registered successfully", HttpStatus.OK);
    }
}
