package com.campusbridge.backend.Controller;

import com.campusbridge.backend.dto.ForumPostRequest;
import com.campusbridge.backend.dto.ForumPostResponse;
import com.campusbridge.backend.service.ForumPostService;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/forum/posts")
public class ForumPostController {

    private final ForumPostService postService;

    public ForumPostController(ForumPostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ForumPostResponse createPost(
            Principal principal,
            @RequestBody ForumPostRequest request) {

        return postService.createPost(
            principal.getName(), request);
    }

    @GetMapping
    public List<ForumPostResponse> getAllPosts(
            Principal principal) {

        return postService.getAllPosts(
            principal.getName());
    }

    @GetMapping("/{postId}")
    public ForumPostResponse getPost(
            Principal principal,
            @PathVariable("postId") Long postId) {

        return postService.getPost(
            principal.getName(), postId);
    }

    @PutMapping("/{postId}")
    public ForumPostResponse updatePost(
            Principal principal,
            @PathVariable("postId") Long postId,
            @RequestBody ForumPostRequest request) {

        return postService.updatePost(
            principal.getName(), postId, request);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(
            Principal principal,
            @PathVariable("postId") Long postId) {

        postService.deletePost(
            principal.getName(), postId);
    }
}
