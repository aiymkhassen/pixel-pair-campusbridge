package com.campusbridge.backend.repository;

import com.campusbridge.backend.entity.ForumComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForumCommentRepository
        extends JpaRepository<ForumComment, Long> {

    List<ForumComment> findByPostIdOrderByCreatedAtAsc(
        Long postId);

    List<ForumComment> findByParentCommentId(
        Long parentCommentId);

    long countByPostId(Long postId);
}
