package com.ksilisk.leetty.web.service.controller;

import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import com.ksilisk.leetty.common.codegen.types.Question;
import com.ksilisk.leetty.web.service.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/question/{titleSlug}")
    public Question getLeetCodeQuestion(@PathVariable("titleSlug") String titleSlug) {
        return questionService.getQuestion(titleSlug);
    }

    @GetMapping("/daily-question")
    public DailyCodingQuestion getDailyQuestion() {
        return questionService.getDailyQuestions();
    }
}
