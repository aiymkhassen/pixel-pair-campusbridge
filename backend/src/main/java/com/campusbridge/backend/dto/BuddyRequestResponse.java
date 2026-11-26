package com.campusbridge.backend.dto;

import com.campusbridge.backend.entity.BuddyRequestStatus;
import java.time.LocalDateTime;

public record BuddyRequestResponse(
    Long id,
    Long senderId,
    String senderName,
    Long receiverId,
    String receiverName,
    BuddyRequestStatus status,
    LocalDateTime createdAt
) {
}
