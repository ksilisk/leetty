package com.ksilisk.leetty.telegram.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableFeignClients
public class LeettyTelegramBotApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(LeettyTelegramBotApplication.class, args);
        String test = run.getBean("test", Test.class).demoClient.hello();
        System.out.println(test);
    }
}
