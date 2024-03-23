package com.ksilisk.leetty.telegram.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LeettyTelegramBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeettyTelegramBotApplication.class, args);
    }
}
