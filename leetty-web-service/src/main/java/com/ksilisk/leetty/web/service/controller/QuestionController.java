package com.ksilisk.leetty.web.service.controller;

import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import com.ksilisk.leetty.common.codegen.types.Question;
import com.ksilisk.leetty.common.question.*;
import com.ksilisk.leetty.web.service.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v{ver}/question")
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/{titleSlug}")
    public Question getLeetCodeQuestion(@PathVariable("titleSlug") String titleSlug) {
        return questionService.getQuestion(titleSlug);
    }

    @GetMapping("/random/{categorySlug}")
    public Question getRandomQuestion(@PathVariable("categorySlug") String categorySlug) {
        return questionService.getRandomQuestion(categorySlug);
    }

    @GetMapping("/daily")
    public DailyCodingQuestion getDailyQuestion() {
        return questionService.getDailyQuestions();
    }

    @GetMapping("/{titleSlug}/acceptance")
    public QuestionAcceptance getAcceptance(@PathVariable("titleSlug") String titleSlug) {
        return questionService.getAcceptance(titleSlug);
    }

    @GetMapping("/{titleSlug}/content")
    public QuestionContent getContent(@PathVariable("titleSlug") String titleSlug) {
        return questionService.getContent(titleSlug);
    }

    @GetMapping("/{titleSlug}/hints")
    public QuestionHints getHints(@PathVariable("titleSlug") String titleSlug) {
        return questionService.getHints(titleSlug);
    }

    @GetMapping("/{titleSlug}/topics")
    public QuestionTopics getTopics(@PathVariable("titleSlug") String titleSlug) {
        return questionService.getTopics(titleSlug);
    }

    @GetMapping("/{titleSlug}/difficulty")
    public QuestionDifficulty getDifficulty(@PathVariable("titleSlug") String titleSlug) {
        return questionService.getDifficulty(titleSlug);
    }
}
