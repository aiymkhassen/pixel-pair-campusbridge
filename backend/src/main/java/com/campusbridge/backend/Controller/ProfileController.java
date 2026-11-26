package com.campusbridge.backend.Controller;

import com.campusbridge.backend.dto.ProfileRequest;
import com.campusbridge.backend.dto.ProfileResponse;
import com.campusbridge.backend.service.ProfileService;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ProfileResponse getProfile(Principal principal) {
        return profileService.getProfile(principal.getName());
    }

    @PutMapping
    public ProfileResponse updateProfile(
            Principal principal,
            @RequestBody ProfileRequest request) {

        return profileService.updateProfile(
                principal.getName(),
                request);
    }
}
