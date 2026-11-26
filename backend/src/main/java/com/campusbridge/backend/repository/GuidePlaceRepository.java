package com.campusbridge.backend.repository;

import com.campusbridge.backend.entity.GuidePlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuidePlaceRepository
        extends JpaRepository<GuidePlace, Long> {

    List<GuidePlace> findAllByOrderByNameAsc();

    List<GuidePlace> findByCategoryIgnoreCase(
        String category);

    List<GuidePlace>
        findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String name,
            String description);
}
