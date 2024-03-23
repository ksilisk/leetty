package com.ksilisk.leetty.telegram.bot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LeettyConfiguration {
    @Bean
    @ConfigurationProperties("leetty")
    public LeettyProperties leettyProperties() {
        return new LeettyProperties();
    }

}
