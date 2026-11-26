package com.campusbridge.backend.repository;

import com.campusbridge.backend.entity.LanguagePhrase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguagePhraseRepository
        extends JpaRepository<LanguagePhrase, Long> {

    List<LanguagePhrase> findByCategoryIgnoreCase(
        String category);

    List<LanguagePhrase> findAllByOrderByIdAsc();

    List<LanguagePhrase>
        findByEnglishContainingIgnoreCaseOrKazakhContainingIgnoreCaseOrRussianContainingIgnoreCase(
            String english,
            String kazakh,
            String russian
        );
}