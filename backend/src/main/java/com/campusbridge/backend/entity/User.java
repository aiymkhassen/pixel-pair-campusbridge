package com.campusbridge.backend.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.STUDENT;
private String course;

private Integer yearOfStudy;

@Enumerated(EnumType.STRING)
private StudentType studentType;

private String spokenLanguage;

private String languageToLearn;

@Column(nullable = false, columnDefinition = "boolean default false")
private boolean availableAsBuddy = false;
    public User() {
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    public String getCourse() {
    return course;
}

public void setCourse(String course) {
    this.course = course;
}

public Integer getYearOfStudy() {
    return yearOfStudy;
}

public void setYearOfStudy(Integer yearOfStudy) {
    this.yearOfStudy = yearOfStudy;
}

public StudentType getStudentType() {
    return studentType;
}

public void setStudentType(StudentType studentType) {
    this.studentType = studentType;
}

public String getSpokenLanguage() {
    return spokenLanguage;
}

public void setSpokenLanguage(String spokenLanguage) {
    this.spokenLanguage = spokenLanguage;
}

public String getLanguageToLearn() {
    return languageToLearn;
}

public void setLanguageToLearn(String languageToLearn) {
    this.languageToLearn = languageToLearn;
}

public boolean isAvailableAsBuddy() {
    return availableAsBuddy;
}

public void setAvailableAsBuddy(boolean availableAsBuddy) {
    this.availableAsBuddy = availableAsBuddy;
}
}
