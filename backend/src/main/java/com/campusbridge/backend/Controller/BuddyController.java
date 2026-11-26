package com.campusbridge.backend.Controller;

import com.campusbridge.backend.dto.BuddyResponse;
import com.campusbridge.backend.service.BuddyService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/buddies")
public class BuddyController {

    private final BuddyService buddyService;

    public BuddyController(BuddyService buddyService) {
        this.buddyService = buddyService;
    }

    @GetMapping("/matches")
    public List<BuddyResponse> getMatches(Principal principal) {
        return buddyService.findMatches(principal.getName());
    }
}
