package com.campusbridge.backend.Controller;

import com.campusbridge.backend.entity.LanguagePhrase;
import com.campusbridge.backend.service.LanguagePhraseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/languages")
public class LanguagePhraseController {

    private final LanguagePhraseService phraseService;

    public LanguagePhraseController(
            LanguagePhraseService phraseService) {

        this.phraseService = phraseService;
    }

    @GetMapping("/phrases")
    public List<LanguagePhrase> getAllPhrases() {
        return phraseService.getAllPhrases();
    }

    @GetMapping("/category/{category}")
    public List<LanguagePhrase> getByCategory(
            @PathVariable("category") String category) {

        return phraseService.getByCategory(category);
    }

    @GetMapping("/search")
    public List<LanguagePhrase> searchPhrases(
            @RequestParam("keyword") String keyword) {

        return phraseService.searchPhrases(keyword);
    }

    @GetMapping("/categories")
    public List<String> getCategories() {
        return phraseService.getCategories();
    }
}
