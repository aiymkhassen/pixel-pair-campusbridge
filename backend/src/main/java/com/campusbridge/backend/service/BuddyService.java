package com.campusbridge.backend.service;

import com.campusbridge.backend.dto.BuddyResponse;
import com.campusbridge.backend.entity.User;
import com.campusbridge.backend.entity.StudentType;
import com.campusbridge.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Comparator;

@Service
public class BuddyService {

    private final UserRepository userRepository;

    public BuddyService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<BuddyResponse> findMatches(String email) {

        User student = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"));

        if (student.getCourse() == null ||
            student.getCourse().isBlank() ||
            student.getYearOfStudy() == null ||
            student.getStudentType() == null) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Complete your academic profile first");
        }

        if (student.getStudentType() != StudentType.INTERNATIONAL) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "Buddy matching is for international students");
        }

        List<User> buddies = userRepository
            .findByStudentTypeAndAvailableAsBuddyTrue(
                StudentType.LOCAL);

        return buddies.stream()
            .filter(buddy -> !buddy.getId().equals(student.getId()))
            .map(buddy -> new BuddyResponse(
                buddy.getId(),
                buddy.getFullName(),
                buddy.getCourse(),
                buddy.getYearOfStudy(),
                buddy.getSpokenLanguage(),
                buddy.getLanguageToLearn(),
                calculateScore(student, buddy)
            ))
            .sorted(Comparator
                .comparingInt(BuddyResponse::matchScore)
                .reversed()
                .thenComparing(BuddyResponse::id))
            .toList();
    }

    private int calculateScore(User student, User buddy) {

        int score = 0;

        if (matches(student.getCourse(), buddy.getCourse())) {
            score += 40;
        }

        if (student.getYearOfStudy().equals(buddy.getYearOfStudy())) {
            score += 20;
        }

        if (matches(student.getLanguageToLearn(),
                    buddy.getSpokenLanguage())) {
            score += 20;
        }

        if (matches(student.getSpokenLanguage(),
                    buddy.getLanguageToLearn())) {
            score += 20;
        }

        return score;
    }

    private boolean matches(String first, String second) {
        return first != null &&
               second != null &&
               !first.isBlank() &&
               !second.isBlank() &&
               first.trim().equalsIgnoreCase(second.trim());
    }
}
