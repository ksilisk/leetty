package com.ksilisk.leetty.telegram.bot.feign;

import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "demo", url = "http://localhost:8181")
public interface DemoClient {
    @GetMapping("/hello")
    String hello();

    @GetMapping("/api/daily-question")
    DailyCodingQuestion getDailyQuestion();
}
