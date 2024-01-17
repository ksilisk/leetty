package com.ksilisk.leetty.telegram.bot;

import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableFeignClients
public class LeettyTelegramBotApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(LeettyTelegramBotApplication.class, args);
        DailyCodingQuestion test = run.getBean("test", Test.class).demoClient.getDailyQuestion();
        System.out.println(test.toString());
    }
}
