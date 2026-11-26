package com.campusbridge.backend.Controller;

import com.campusbridge.backend.entity.GuidePlace;
import com.campusbridge.backend.service.GuidePlaceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guide")
public class GuidePlaceController {

    private final GuidePlaceService guideService;

    public GuidePlaceController(
            GuidePlaceService guideService) {

        this.guideService = guideService;
    }

    @GetMapping("/places")
    public List<GuidePlace> getAllPlaces() {
        return guideService.getAllPlaces();
    }

    @GetMapping("/category/{category}")
    public List<GuidePlace> getByCategory(
            @PathVariable("category") String category) {

        return guideService.getByCategory(category);
    }

    @GetMapping("/search")
    public List<GuidePlace> searchPlaces(
            @RequestParam("keyword") String keyword) {

        return guideService.searchPlaces(keyword);
    }

    @GetMapping("/categories")
    public List<String> getCategories() {
        return guideService.getCategories();
    }
}
