package com.campusbridge.backend.repository;

import com.campusbridge.backend.entity.BuddyRequest;
import com.campusbridge.backend.entity.BuddyRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BuddyRequestRepository
        extends JpaRepository<BuddyRequest, Long> {

    Optional<BuddyRequest> findBySenderIdAndReceiverId(
        Long senderId, Long receiverId);

    List<BuddyRequest> findByReceiverId(Long receiverId);

    List<BuddyRequest> findBySenderId(Long senderId);

    List<BuddyRequest> findBySenderIdAndStatus(
        Long senderId, BuddyRequestStatus status);

    List<BuddyRequest> findByReceiverIdAndStatus(
        Long receiverId, BuddyRequestStatus status);
}
