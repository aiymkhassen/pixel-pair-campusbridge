package com.campusbridge.backend.Controller;

import com.campusbridge.backend.dto.BuddyRequestResponse;
import com.campusbridge.backend.service.BuddyRequestService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/buddy-requests")
public class BuddyRequestController {

    private final BuddyRequestService requestService;

    public BuddyRequestController(
            BuddyRequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("/send/{receiverId}")
    @ResponseStatus(HttpStatus.CREATED)
    public BuddyRequestResponse sendRequest(
            Principal principal,
            @PathVariable("receiverId") Long receiverId) {

        return requestService.sendRequest(
            principal.getName(), receiverId);
    }

    @GetMapping("/incoming")
    public List<BuddyRequestResponse> getIncoming(
            Principal principal) {

        return requestService.getIncoming(
            principal.getName());
    }

    @GetMapping("/outgoing")
    public List<BuddyRequestResponse> getOutgoing(
            Principal principal) {

        return requestService.getOutgoing(
            principal.getName());
    }

    @PutMapping("/{requestId}/accept")
    public BuddyRequestResponse acceptRequest(
            Principal principal,
            @PathVariable("requestId") Long requestId) {

        return requestService.respondToRequest(
            principal.getName(), requestId, true);
    }

    @PutMapping("/{requestId}/decline")
    public BuddyRequestResponse declineRequest(
            Principal principal,
            @PathVariable("requestId") Long requestId) {

        return requestService.respondToRequest(
            principal.getName(), requestId, false);
    }

    @GetMapping("/accepted")
    public List<BuddyRequestResponse> getAcceptedBuddies(
            Principal principal) {

        return requestService.getAcceptedBuddies(
            principal.getName());
    }
}
