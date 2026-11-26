package com.campusbridge.backend.service;

import com.campusbridge.backend.dto.BuddyRequestResponse;
import com.campusbridge.backend.entity.*;
import com.campusbridge.backend.repository.BuddyRequestRepository;
import com.campusbridge.backend.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.ArrayList;

@Service
public class BuddyRequestService {

    private final BuddyRequestRepository requestRepository;
    private final UserRepository userRepository;

    public BuddyRequestService(
            BuddyRequestRepository requestRepository,
            UserRepository userRepository) {

        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
    }

    // Send a buddy request
    @Transactional
    public BuddyRequestResponse sendRequest(
            String email, Long receiverId) {

        User sender = findUser(email);

        User receiver = userRepository.findById(receiverId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Student not found"));

        if (sender.getCourse() == null ||
            sender.getCourse().isBlank() ||
            sender.getYearOfStudy() == null ||
            sender.getStudentType() == null) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Complete your academic profile first");
        }

        if (sender.getId().equals(receiverId)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "You cannot send a request to yourself");
        }

        if (sender.getStudentType() != StudentType.INTERNATIONAL ||
            receiver.getStudentType() != StudentType.LOCAL ||
            !receiver.isAvailableAsBuddy()) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "This student is not available for buddy matching");
        }

        if (requestRepository
                .findBySenderIdAndReceiverId(
                    sender.getId(), receiverId)
                .isPresent()) {

            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Buddy request already exists");
        }

        BuddyRequest request = new BuddyRequest();

        request.setSender(sender);
        request.setReceiver(receiver);
        request.setStatus(BuddyRequestStatus.PENDING);

        try {
            return toResponse(
                requestRepository.saveAndFlush(request));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Unable to create duplicate buddy request");
        }
    }

    // View incoming requests
    public List<BuddyRequestResponse> getIncoming(String email) {

        User user = findUser(email);

        return requestRepository.findByReceiverId(user.getId())
            .stream()
            .map(this::toResponse)
            .toList();
    }

    // View outgoing requests
    public List<BuddyRequestResponse> getOutgoing(String email) {

        User user = findUser(email);

        return requestRepository.findBySenderId(user.getId())
            .stream()
            .map(this::toResponse)
            .toList();
    }

    // Accept or decline a request
    @Transactional
    public BuddyRequestResponse respondToRequest(
            String email, Long requestId, boolean accept) {

        User user = findUser(email);

        BuddyRequest request = requestRepository.findById(requestId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Request not found"));

        if (!request.getReceiver().getId().equals(user.getId())) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "You cannot respond to this request");
        }

        if (request.getStatus() != BuddyRequestStatus.PENDING) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "This request has already been answered");
        }

        if (accept &&
            (user.getStudentType() != StudentType.LOCAL ||
             !user.isAvailableAsBuddy() ||
             request.getSender().getStudentType()
                 != StudentType.INTERNATIONAL)) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Students are no longer eligible for this match");
        }

        request.setStatus(
            accept
                ? BuddyRequestStatus.ACCEPTED
                : BuddyRequestStatus.DECLINED
        );

        return toResponse(requestRepository.save(request));
    }

    // View accepted buddies
    public List<BuddyRequestResponse> getAcceptedBuddies(
            String email) {

        User user = findUser(email);

        List<BuddyRequest> requests = new ArrayList<>();

        requests.addAll(
            requestRepository.findBySenderIdAndStatus(
                user.getId(), BuddyRequestStatus.ACCEPTED));

        requests.addAll(
            requestRepository.findByReceiverIdAndStatus(
                user.getId(), BuddyRequestStatus.ACCEPTED));

        return requests.stream()
            .map(this::toResponse)
            .toList();
    }

    private User findUser(String email) {

        return userRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"));
    }

    private BuddyRequestResponse toResponse(BuddyRequest request) {

        return new BuddyRequestResponse(
            request.getId(),
            request.getSender().getId(),
            request.getSender().getFullName(),
            request.getReceiver().getId(),
            request.getReceiver().getFullName(),
            request.getStatus(),
            request.getCreatedAt()
        );
    }
}
