package com.campusbridge.backend.service;

import com.campusbridge.backend.dto.ForumCommentRequest;
import com.campusbridge.backend.dto.ForumCommentResponse;
import com.campusbridge.backend.entity.*;
import com.campusbridge.backend.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ForumCommentService {

    private final ForumCommentRepository commentRepository;
    private final ForumPostRepository postRepository;
    private final UserRepository userRepository;

    public ForumCommentService(
            ForumCommentRepository commentRepository,
            ForumPostRepository postRepository,
            UserRepository userRepository) {

        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ForumCommentResponse createComment(
            String email, Long postId,
            ForumCommentRequest request) {

        User user = findUser(email);
        ForumPost post = findPost(postId);

        validateContent(request.content());

        ForumComment comment = new ForumComment();

        comment.setPost(post);
        comment.setAuthor(user);
        comment.setContent(request.content().trim());
        comment.setAnonymous(request.anonymous());

        if (request.parentCommentId() != null) {

            ForumComment parent =
                findComment(request.parentCommentId());

            if (!parent.getPost().getId().equals(postId)) {

                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Comment belongs to another post");
            }

            comment.setParentComment(parent);
        }

        return toResponse(
            commentRepository.save(comment), user);
    }

    @Transactional(readOnly = true)
    public List<ForumCommentResponse> getComments(
            String email, Long postId) {

        User user = findUser(email);
        findPost(postId);

        return commentRepository
            .findByPostIdOrderByCreatedAtAsc(postId)
            .stream()
            .map(comment -> toResponse(comment, user))
            .toList();
    }

    @Transactional
    public ForumCommentResponse updateComment(
            String email, Long commentId,
            ForumCommentRequest request) {

        User user = findUser(email);
        ForumComment comment = findComment(commentId);

        checkOwnership(comment, user);
        validateContent(request.content());

        comment.setContent(request.content().trim());
        comment.setAnonymous(request.anonymous());

        return toResponse(
            commentRepository.save(comment), user);
    }

    @Transactional
    public void deleteComment(
            String email, Long commentId) {

        User user = findUser(email);
        ForumComment comment = findComment(commentId);

        checkOwnership(comment, user);

        if (!commentRepository
                .findByParentCommentId(commentId)
                .isEmpty()) {

            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Cannot delete a comment with replies");
        }

        commentRepository.delete(comment);
    }

    private User findUser(String email) {

        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"));

        if (user.getCourse() == null ||
            user.getCourse().isBlank() ||
            user.getYearOfStudy() == null ||
            user.getStudentType() == null) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Complete your community profile first");
        }

        return user;
    }

    private ForumPost findPost(Long postId) {

        return postRepository.findById(postId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Post not found"));
    }

    private ForumComment findComment(Long commentId) {

        return commentRepository.findById(commentId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Comment not found"));
    }

    private void checkOwnership(
            ForumComment comment, User user) {

        if (!comment.getAuthor().getId().equals(user.getId())) {

            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "You can only modify your own comments");
        }
    }

    private void validateContent(String content) {

        if (content == null || content.isBlank()) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Comment cannot be empty");
        }

        if (content.trim().length() > 2000) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Comment is too long");
        }
    }

    private ForumCommentResponse toResponse(
            ForumComment comment, User viewer) {

        User author = comment.getAuthor();

        boolean anonymous = comment.isAnonymous();

        boolean ownComment =
            author.getId().equals(viewer.getId());

        return new ForumCommentResponse(
            comment.getId(),
            comment.getPost().getId(),
            comment.getParentComment() == null
                ? null : comment.getParentComment().getId(),
            anonymous ? null : author.getId(),
            anonymous ? "Anonymous Student" : author.getFullName(),
            comment.getContent(),
            anonymous,
            ownComment,
            comment.getCreatedAt()
        );
    }
}
