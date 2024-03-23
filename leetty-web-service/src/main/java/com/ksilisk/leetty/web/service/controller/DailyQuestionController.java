package com.ksilisk.leetty.web.service.controller;

import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import com.ksilisk.leetty.web.service.service.DailyQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/daily-question")
@RequiredArgsConstructor
public class DailyQuestionController {
    private final DailyQuestionService dailyQuestionService;

    @GetMapping
    public DailyCodingQuestion dailyCodingQuestion() {
        return dailyQuestionService.getQuestion();
    }

    @GetMapping("/chats")
    public List<Long> getChatsToSendDaily(@RequestParam("time") String time) {
        return dailyQuestionService.getChatsToSendDaily(time);
    }
}
