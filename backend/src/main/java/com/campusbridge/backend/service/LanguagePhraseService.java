package com.campusbridge.backend.service;

import com.campusbridge.backend.entity.LanguagePhrase;
import com.campusbridge.backend.repository.LanguagePhraseRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguagePhraseService {

    private final LanguagePhraseRepository phraseRepository;

    public LanguagePhraseService(
            LanguagePhraseRepository phraseRepository) {

        this.phraseRepository = phraseRepository;
    }

    public List<LanguagePhrase> getAllPhrases() {

        return phraseRepository.findAllByOrderByIdAsc();
    }

    public List<LanguagePhrase> getByCategory(String category) {

        return phraseRepository
            .findByCategoryIgnoreCase(category.trim());
    }

    public List<LanguagePhrase> searchPhrases(String keyword) {

        if (keyword == null || keyword.isBlank()) {
            return getAllPhrases();
        }

        String search = keyword.trim();

        return phraseRepository
            .findByEnglishContainingIgnoreCaseOrKazakhContainingIgnoreCaseOrRussianContainingIgnoreCase(
                search, search, search);
    }

    public List<String> getCategories() {

        return phraseRepository.findAll()
            .stream()
            .map(LanguagePhrase::getCategory)
            .distinct()
            .sorted()
            .toList();
    }
}
