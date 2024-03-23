package com.ksilisk.leetty.telegram.bot.client;

import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(url = "${leetty.web-service-url}", name = "dailyQuestionClient")
public interface DailyQuestionClient {
    @GetMapping("/api/daily-question")
    DailyCodingQuestion getDailyQuestion();

    @GetMapping(value = "/api/daily-question/chats")
    List<Long> getUsersToSendDaily(@RequestParam("time") String time);
}
