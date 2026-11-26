package com.campusbridge.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "language_phrases")
public class LanguagePhrase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String english;

    @Column(nullable = false)
    private String kazakh;

    @Column(nullable = false)
    private String russian;

    public Long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getKazakh() {
        return kazakh;
    }

    public void setKazakh(String kazakh) {
        this.kazakh = kazakh;
    }

    public String getRussian() {
        return russian;
    }

    public void setRussian(String russian) {
        this.russian = russian;
    }
}
