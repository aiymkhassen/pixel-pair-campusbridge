package com.campusbridge.backend.Controller;

import com.campusbridge.backend.dto.ForumCommentRequest;
import com.campusbridge.backend.dto.ForumCommentResponse;
import com.campusbridge.backend.service.ForumCommentService;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/forum")
public class ForumCommentController {

    private final ForumCommentService commentService;

    public ForumCommentController(
            ForumCommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public ForumCommentResponse createComment(
            Principal principal,
            @PathVariable("postId") Long postId,
            @RequestBody ForumCommentRequest request) {

        return commentService.createComment(
            principal.getName(), postId, request);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<ForumCommentResponse> getComments(
            Principal principal,
            @PathVariable("postId") Long postId) {

        return commentService.getComments(
            principal.getName(), postId);
    }

    @PutMapping("/comments/{commentId}")
    public ForumCommentResponse updateComment(
            Principal principal,
            @PathVariable("commentId") Long commentId,
            @RequestBody ForumCommentRequest request) {

        return commentService.updateComment(
            principal.getName(), commentId, request);
    }

    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(
            Principal principal,
            @PathVariable("commentId") Long commentId) {

        commentService.deleteComment(
            principal.getName(), commentId);
    }
}
