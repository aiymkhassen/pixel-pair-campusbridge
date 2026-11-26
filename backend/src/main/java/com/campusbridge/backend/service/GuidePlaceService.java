package com.campusbridge.backend.service;

import com.campusbridge.backend.entity.GuidePlace;
import com.campusbridge.backend.repository.GuidePlaceRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuidePlaceService {

    private final GuidePlaceRepository guideRepository;

    public GuidePlaceService(
            GuidePlaceRepository guideRepository) {

        this.guideRepository = guideRepository;
    }

    public List<GuidePlace> getAllPlaces() {

        return guideRepository.findAllByOrderByNameAsc();
    }

    public List<GuidePlace> getByCategory(String category) {

        return guideRepository
            .findByCategoryIgnoreCase(category.trim());
    }

    public List<GuidePlace> searchPlaces(String keyword) {

        if (keyword == null || keyword.isBlank()) {
            return getAllPlaces();
        }

        String search = keyword.trim();

        return guideRepository
            .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                search, search);
    }

    public List<String> getCategories() {

        return guideRepository.findAll()
            .stream()
            .map(GuidePlace::getCategory)
            .distinct()
            .sorted()
            .toList();
    }
}
