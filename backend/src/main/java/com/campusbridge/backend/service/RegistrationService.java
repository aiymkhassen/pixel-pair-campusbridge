package com.campusbridge.backend.service;
import com.campusbridge.backend.entity.User;
import com.campusbridge.backend.entity.Role;
import com.campusbridge.backend.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
@Service
public class RegistrationService {
     private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String fullName, String email, String password) {

        if (userRepository.existsByEmail(email)) {
    throw new ResponseStatusException(
        HttpStatus.CONFLICT,
        "Email is already registered");
}

        User user = new User();

        user.setFullName(fullName);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setRole(Role.STUDENT);

        return userRepository.save(user);
    }
}
