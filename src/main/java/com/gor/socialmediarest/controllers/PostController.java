package com.gor.socialmediarest.controllers;

import com.gor.socialmediarest.requests.PostRequest;
import com.gor.socialmediarest.services.PostService;
import com.gor.socialmediarest.utils.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {
    private final ResponseBuilder responseBuilder;
    private final PostService postService;
    
    @GetMapping("/posts")
    public ResponseEntity<?> getPosts() {
        return responseBuilder.buildResponse("Posts are fetched successfully", HttpStatus.OK,
                postService.fetchAllPosts()
        );
    }
    
    @GetMapping("/post/{id}")
    public ResponseEntity<?> getPostById(@PathVariable long id) throws Throwable {
        return responseBuilder.buildResponse("Post is fetched successfully", HttpStatus.OK,
                postService.fetchPostById(id)
        );
    }
    
    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody PostRequest request) {
        postService.createPost(request);
        return responseBuilder.buildResponse("Post is created successfully", HttpStatus.OK);
    }
    
    @PutMapping("/post/{id}")
    public ResponseEntity<?> updatePost(@PathVariable long id, @RequestBody PostRequest request) {
        postService.updatePost(id, request);
        return responseBuilder.buildResponse("Post is edited successfully", HttpStatus.OK);
    }
    
    @DeleteMapping("/post/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletePost(@PathVariable long id) {
        postService.deletePost(id);
        return responseBuilder.buildResponse("Post is deleted successfully", HttpStatus.OK);
    }

    @Autowired
    public PostController(ResponseBuilder responseBuilder, PostService postService) {
        this.responseBuilder = responseBuilder;
        this.postService = postService;
    }
}
