package com.campusbridge.backend.repository;

import com.campusbridge.backend.entity.ForumPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForumPostRepository
        extends JpaRepository<ForumPost, Long> {

    List<ForumPost> findAllByOrderByCreatedAtDesc();

    List<ForumPost> findByAuthorIdOrderByCreatedAtDesc(
        Long authorId);
}
