package com.campusbridge.backend.service;

import com.campusbridge.backend.dto.ForumPostRequest;
import com.campusbridge.backend.dto.ForumPostResponse;
import com.campusbridge.backend.entity.ForumPost;
import com.campusbridge.backend.entity.User;
import com.campusbridge.backend.repository.ForumPostRepository;
import com.campusbridge.backend.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ForumPostService {

    private final ForumPostRepository postRepository;
    private final UserRepository userRepository;

    public ForumPostService(
            ForumPostRepository postRepository,
            UserRepository userRepository) {

        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // Create a post
    @Transactional
    public ForumPostResponse createPost(
            String email, ForumPostRequest request) {

        User user = findUser(email);
        validatePost(request);

        ForumPost post = new ForumPost();

        post.setAuthor(user);
        post.setTitle(request.title().trim());
        post.setContent(request.content().trim());
        post.setAnonymous(request.anonymous());

        return toResponse(postRepository.save(post), user);
    }

    // View all posts
    @Transactional(readOnly = true)
    public List<ForumPostResponse> getAllPosts(String email) {

        User user = findUser(email);

        return postRepository.findAllByOrderByCreatedAtDesc()
            .stream()
            .map(post -> toResponse(post, user))
            .toList();
    }

    // View a single post
    @Transactional(readOnly = true)
    public ForumPostResponse getPost(String email, Long postId) {

        User user = findUser(email);

        return toResponse(findPost(postId), user);
    }

    // Edit your own post
    @Transactional
    public ForumPostResponse updatePost(
            String email, Long postId,
            ForumPostRequest request) {

        User user = findUser(email);
        ForumPost post = findPost(postId);

        checkOwnership(post, user);
        validatePost(request);

        post.setTitle(request.title().trim());
        post.setContent(request.content().trim());
        post.setAnonymous(request.anonymous());

        return toResponse(postRepository.save(post), user);
    }

    // Delete your own post
    @Transactional
    public void deletePost(String email, Long postId) {

        User user = findUser(email);
        ForumPost post = findPost(postId);

        checkOwnership(post, user);

        postRepository.delete(post);
    }

    // Find user and check academic profile
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

    private void checkOwnership(ForumPost post, User user) {

        if (!post.getAuthor().getId().equals(user.getId())) {

            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "You can only modify your own posts");
        }
    }

    private void validatePost(ForumPostRequest request) {

        if (request.title() == null ||
            request.title().isBlank()) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Title is required");
        }

        if (request.title().trim().length() > 200) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Title is too long");
        }

        if (request.content() == null ||
            request.content().isBlank()) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Content is required");
        }

        if (request.content().trim().length() > 5000) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Content is too long");
        }
    }

    // Protect anonymous identities
    private ForumPostResponse toResponse(
            ForumPost post, User viewer) {

        User author = post.getAuthor();

        boolean anonymous = post.isAnonymous();

        boolean ownPost =
            author.getId().equals(viewer.getId());

        return new ForumPostResponse(
            post.getId(),
            anonymous ? null : author.getId(),
            anonymous ? "Anonymous Student" : author.getFullName(),
            anonymous ? null : author.getCourse(),
            anonymous ? null : author.getYearOfStudy(),
            post.getTitle(),
            post.getContent(),
            anonymous,
            ownPost,
            post.getCreatedAt()
        );
    }
}
