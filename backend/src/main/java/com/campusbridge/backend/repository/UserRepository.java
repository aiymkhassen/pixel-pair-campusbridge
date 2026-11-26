package com.campusbridge.backend.repository;

import com.campusbridge.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.campusbridge.backend.entity.StudentType;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    List<User> findByStudentTypeAndAvailableAsBuddyTrue(StudentType studentType);
}