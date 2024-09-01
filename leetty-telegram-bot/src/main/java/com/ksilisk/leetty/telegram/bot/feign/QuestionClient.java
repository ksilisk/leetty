package com.ksilisk.leetty.telegram.bot.feign;

import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import com.ksilisk.leetty.common.codegen.types.Question;
import com.ksilisk.leetty.common.dto.question.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${leetty.web-service-url}", name = "questionClient")
public interface QuestionClient {
    @GetMapping("/api/question/{titleSlug}")
    Question getLeetCodeQuestion(@PathVariable("titleSlug") String titleSlug);

    @GetMapping("/api/daily-question")
    DailyCodingQuestion getDailyQuestion();

    @GetMapping("/api/question/{titleSlug}/hints")
    QuestionHints getHints(@PathVariable("titleSlug") String titleSlug);

    @GetMapping("/api/question/{titleSlug}/acceptance")
    QuestionAcceptance getAcceptance(@PathVariable("titleSlug") String titleSlug);

    @GetMapping("/api/question/{titleSlug}/content")
    QuestionContent getContent(@PathVariable("titleSlug") String titleSlug);

    @GetMapping("/api/question/{titleSlug}/topics")
    QuestionTopics getTopics(@PathVariable("titleSlug") String titleSlug);

    @GetMapping("/api/question/{titleSlug}/difficulty")
    QuestionDifficulty getDifficulty(@PathVariable("titleSlug") String titleSlug);
}
