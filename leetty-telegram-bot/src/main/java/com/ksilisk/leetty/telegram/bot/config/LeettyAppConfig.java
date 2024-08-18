package com.ksilisk.leetty.telegram.bot.config;

import com.ksilisk.telegram.bot.starter.processor.CompositeUpdateProcessor;
import com.ksilisk.telegram.bot.starter.processor.UpdateProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Configuration
public class LeettyAppConfig {
    @Bean
    @Primary
    public UpdateProcessor updateProcessor(List<UpdateProcessor> updateProcessors) {
        return new CompositeUpdateProcessor(updateProcessors);
    }
}
