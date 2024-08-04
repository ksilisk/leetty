package com.ksilisk.telegram.bot.starter.annotation;

import com.ksilisk.telegram.bot.starter.config.TelegramBotAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
@ConditionalOnBean(TelegramBotAutoConfiguration.TelegramBotLongPollingConfiguration.class)
public @interface LongPollingBot {
}
