package com.ksilisk.leetty.telegram.bot.feign;

import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import com.ksilisk.leetty.common.codegen.types.Question;
import com.ksilisk.leetty.common.dto.question.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${leetty.web-service-url}/v1/question", name = "questionClient")
public interface QuestionClient {
    @GetMapping("/{titleSlug}")
    Question getLeetCodeQuestion(@PathVariable("titleSlug") String titleSlug);

    @GetMapping("/daily")
    DailyCodingQuestion getDailyQuestion();

    @GetMapping("/random/{categorySlug}")
    Question getRandomQuestion(@PathVariable("categorySlug") String categorySlug);

    @GetMapping("/{titleSlug}/hints")
    QuestionHints getHints(@PathVariable("titleSlug") String titleSlug);

    @GetMapping("/{titleSlug}/acceptance")
    QuestionAcceptance getAcceptance(@PathVariable("titleSlug") String titleSlug);

    @GetMapping("/{titleSlug}/content")
    QuestionContent getContent(@PathVariable("titleSlug") String titleSlug);

    @GetMapping("/{titleSlug}/topics")
    QuestionTopics getTopics(@PathVariable("titleSlug") String titleSlug);

    @GetMapping("/{titleSlug}/difficulty")
    QuestionDifficulty getDifficulty(@PathVariable("titleSlug") String titleSlug);
}
