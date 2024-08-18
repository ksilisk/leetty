package com.ksilisk.leetty.telegram.bot.feign;

import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import com.ksilisk.leetty.common.codegen.types.Question;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${leetty.web-service-url}", name = "questionClient")
public interface QuestionClient {
    @GetMapping("/api/question/{titleSlug}")
    Question getLeetCodeQuestion(@PathVariable("titleSlug") String titleSlug);

    @GetMapping("/api/daily-question")
    DailyCodingQuestion getDailyQuestion();
}
