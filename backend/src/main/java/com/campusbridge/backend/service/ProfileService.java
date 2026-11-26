package com.campusbridge.backend.service;

import com.campusbridge.backend.dto.ProfileRequest;
import com.campusbridge.backend.dto.ProfileResponse;
import com.campusbridge.backend.entity.User;
import com.campusbridge.backend.entity.StudentType;
import com.campusbridge.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProfileService {

    private final UserRepository userRepository;

    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ProfileResponse updateProfile(
            String email, ProfileRequest request) {

        User user = findUser(email);

        if (request.course() == null ||
                request.course().isBlank()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Course is required");
        }

        if (request.yearOfStudy() == null ||
                request.yearOfStudy() < 1 ||
                request.yearOfStudy() > 4) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Year must be between 1 and 4");
        }

        if (request.studentType() == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Student type is required");
        }

        user.setCourse(request.course().trim());
        user.setYearOfStudy(request.yearOfStudy());
        user.setStudentType(request.studentType());
        user.setSpokenLanguage(request.spokenLanguage());
        user.setLanguageToLearn(request.languageToLearn());

        user.setAvailableAsBuddy(
            request.studentType() == StudentType.LOCAL
            && request.availableAsBuddy()
        );

        userRepository.save(user);

        return toResponse(user);
    }

    public ProfileResponse getProfile(String email) {
        return toResponse(findUser(email));
    }

    private User findUser(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "User not found"));
    }

    private ProfileResponse toResponse(User user) {
        return new ProfileResponse(
            user.getId(),
            user.getFullName(),
            user.getEmail(),
            user.getCourse(),
            user.getYearOfStudy(),
            user.getStudentType(),
            user.getSpokenLanguage(),
            user.getLanguageToLearn(),
            user.isAvailableAsBuddy()
        );
    }
}
